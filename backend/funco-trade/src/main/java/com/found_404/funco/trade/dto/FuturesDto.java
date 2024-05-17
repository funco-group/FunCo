package com.found_404.funco.trade.dto;

import com.found_404.funco.trade.domain.FutureTrade;
import com.found_404.funco.trade.domain.type.TradeType;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record FuturesDto(
    Long id,
    String ticker,
    TradeType tradeType,
    Long orderCash,
    Long price,
    Integer leverage,
    Long settlement,
    LocalDateTime tradeDate
) {

    public static FuturesDto fromEntity(FutureTrade futureTrade) {
        return FuturesDto.builder()
                .id(futureTrade.getId())
                .ticker(futureTrade.getTicker())
                .tradeType(futureTrade.getTradeType())
                .orderCash(futureTrade.getOrderCash())
                .price(futureTrade.getPrice())
                .leverage(futureTrade.getLeverage())
                .settlement(futureTrade.getSettlement())
                .tradeDate(futureTrade.getCreatedAt())
                .build();
    }

}
