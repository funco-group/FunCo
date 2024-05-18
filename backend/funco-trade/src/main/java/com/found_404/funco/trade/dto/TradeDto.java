package com.found_404.funco.trade.dto;

import java.time.LocalDateTime;

import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.domain.type.TradeType;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TradeDto(
        Long id,
        String ticker,
        TradeType tradeType,
        Double volume,
        Long orderCash,
        Double price,
        LocalDateTime tradeDate
) {

    public static TradeDto fromEntity(Trade trade) {
        return TradeDto.builder()
                .id(trade.getId())
                .ticker(trade.getTicker())
                .price(trade.getPrice())
                .volume(trade.getVolume())
                .orderCash(trade.getOrderCash())
                .tradeType(trade.getTradeType())
                .tradeDate(trade.getCreatedAt())
                .build();
    }
}
