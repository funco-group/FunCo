package com.found_404.funco.feignClient.dto;

public record HoldingCoinResponse(
        String ticker,
        Double volume) {
}
