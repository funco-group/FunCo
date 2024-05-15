package com.found_404.funcomember.feignClient.dto;

public record CoinValuation (
	String ticker,
	Long price,
	Long valuation) {
}
