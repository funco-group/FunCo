package com.found_404.funco.follow.dto;

import com.found_404.funco.follow.domain.type.TradeType;

public record Trade(
	Long id,
	Long memberId,
	String ticker,
	TradeType tradeType,
	Double volume,
	Long orderCash,
	Double price
) {
}
