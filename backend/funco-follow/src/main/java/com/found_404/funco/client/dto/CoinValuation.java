package com.found_404.funco.client.dto;

public record CoinValuation (
	String ticker,
	Long price,
	Long valuation) {
}
