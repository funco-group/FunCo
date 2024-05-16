package com.found_404.funco.asset.dto;


import lombok.Builder;

@Builder
public record ActiveFutureInfo(
	String ticker,
	CoinTradeType coinTradeType,
	Long orderCash,
	Long price,
	Integer leverage
) {
}
