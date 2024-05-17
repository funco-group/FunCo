package com.found_404.funco.trade.dto;

import com.found_404.funco.trade.domain.ActiveFuture;
import com.found_404.funco.trade.domain.type.TradeType;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record ActiveFutureDto(
        Long id,
        String ticker,
        TradeType tradeType,
        Long orderCash,
        Long price,
        LocalDateTime tradeDate,
        Integer leverage
) {
    public static ActiveFutureDto fromEntity(ActiveFuture activeFuture) {
        return ActiveFutureDto.builder()
                .id(activeFuture.getId())
                .ticker(activeFuture.getTicker())
                .tradeType(activeFuture.getTradeType())
                .orderCash(activeFuture.getOrderCash())
                .price(activeFuture.getPrice())
                .tradeDate(activeFuture.getCreatedAt())
                .leverage(activeFuture.getLeverage())
                .build();
    }
}
