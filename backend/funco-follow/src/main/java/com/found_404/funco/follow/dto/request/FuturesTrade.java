package com.found_404.funco.follow.dto.request;

import com.found_404.funco.follow.domain.type.TradeType;

public record FuturesTrade(
        Long memberId,
        String ticker,
        TradeType tradeType,
        Long orderCash,
        Double price,
        Integer leverage,
        Long settlement
) {
}
