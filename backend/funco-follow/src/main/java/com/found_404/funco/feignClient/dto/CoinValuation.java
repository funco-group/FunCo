package com.found_404.funco.feignClient.dto;

public record CoinValuation (
	String ticker,
	Long price,
	Long valuation) {
}
