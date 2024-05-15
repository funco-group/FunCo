package com.found_404.funco.feignClient.dto;

import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.domain.type.TradeType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record FollowTradeRequest(
        Long id,
        Long memberId,
        String ticker,
        TradeType tradeType,
        Double volume,
        Long orderCash,
        Long price
) {
    public static FollowTradeRequest fromEntity(Trade trade) {
        return FollowTradeRequest.builder()
                .id(trade.getId())
                .memberId(trade.getMemberId())
                .ticker(trade.getTicker())
                .tradeType(trade.getTradeType())
                .volume(trade.getVolume())
                .orderCash(trade.getOrderCash())
                .price(trade.getPrice())
                .build();
    }
}
