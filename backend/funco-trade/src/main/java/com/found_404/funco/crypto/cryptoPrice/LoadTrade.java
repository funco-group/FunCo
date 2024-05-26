package com.found_404.funco.crypto.cryptoPrice;

import com.found_404.funco.trade.domain.ActiveFuture;
import com.found_404.funco.trade.domain.OpenTrade;
import com.found_404.funco.trade.domain.type.TradeType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record LoadTrade(
        TradeType tradeType,
        String ticker,
        Long id,
        Double price
) {

    public static LoadTrade getLoadTrade(OpenTrade openTrade) {
        return LoadTrade.builder()
                .tradeType(openTrade.getTradeType())
                .ticker(openTrade.getTicker())
                .id(openTrade.getId())
                .price(openTrade.getPrice())
                .build();
    }

    public static LoadTrade getLoadTrade(ActiveFuture activeFuture) {
        return LoadTrade.builder()
                .tradeType(activeFuture.getTradeType())
                .ticker(activeFuture.getTicker())
                .id(activeFuture.getId())
                .price(activeFuture.getLiquidatedPrice())
                .build();
    }

}
