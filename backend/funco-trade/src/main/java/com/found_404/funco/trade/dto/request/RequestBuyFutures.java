package com.found_404.funco.trade.dto.request;

public record RequestBuyFutures(
        String ticker,
        Long orderCash,
        Integer leverage
) {

}
