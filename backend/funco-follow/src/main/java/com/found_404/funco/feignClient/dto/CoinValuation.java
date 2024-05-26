package com.found_404.funco.feignClient.dto;

public record CoinValuation (
	String ticker,
	Double price,
	Long valuation) {
}
