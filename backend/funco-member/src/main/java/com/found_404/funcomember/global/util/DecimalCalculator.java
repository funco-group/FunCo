package com.found_404.funcomember.global.util;

import static java.math.RoundingMode.*;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class DecimalCalculator {
    @RequiredArgsConstructor
    @Getter
    public enum ScaleType {
        VOLUME_SCALE(10),       // 코인 수량
        RETURN_RATE_SCALE(2),   // 수익률
        CASH_SCALE(0),          // 현금
        NORMAL_SCALE(30),       // 일반적인 연산의 경우
        ;

        private final int scale;
    }

    public static double multiple(double a, double b, ScaleType scaleType) {
        return BigDecimalMultiple(a, b, scaleType.getScale());
    }

    public static double multiple(long a, double b, ScaleType scaleType) {
        return BigDecimalMultiple((double) a, b, scaleType.getScale());
    }

    public static double divide(double a, double b, ScaleType scaleType) {
        return bigDecimalDivide(a, b, scaleType.getScale());
    }

    public static double divide(long a, double b, ScaleType scaleType) {
        return bigDecimalDivide((double) a, b, scaleType.getScale());
    }

    public static double divide(long a, long b, ScaleType scaleType) {
        return bigDecimalDivide((double) a, (double) b, scaleType.getScale());
    }


    // 구현체
    public static double plus(double a, double b, ScaleType scaleType) {
        BigDecimal bda = BigDecimal.valueOf(a);
        BigDecimal bdb = BigDecimal.valueOf(b);

        return bda.add(bdb)
                .setScale(scaleType.getScale(), DOWN)
                .doubleValue();
    }

    public static double minus(double a, double b, ScaleType scaleType) {
        BigDecimal bda = BigDecimal.valueOf(a);
        BigDecimal bdb = BigDecimal.valueOf(b);

        return bda.subtract(bdb)
                .setScale(scaleType.getScale(), DOWN)
                .doubleValue();
    }

    private static double BigDecimalMultiple(double a, double b, int scale) {
        BigDecimal bda = BigDecimal.valueOf(a);
        BigDecimal bdb = BigDecimal.valueOf(b);

        return bda.multiply(bdb)
                .setScale(scale, DOWN)
                .doubleValue();
    }

    private static double bigDecimalDivide(double a, double b, int scale) {
        BigDecimal bda = BigDecimal.valueOf(a);
        BigDecimal bdb = BigDecimal.valueOf(b);

        return bda.divide(bdb, scale, DOWN)
                .setScale(scale, DOWN)
                .doubleValue();
    }

}
