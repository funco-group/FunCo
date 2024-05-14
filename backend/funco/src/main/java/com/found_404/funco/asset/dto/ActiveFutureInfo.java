package com.found_404.funco.asset.dto;

import com.found_404.funco.trade.domain.type.TradeType;

import lombok.Builder;

@Builder
public record ActiveFutureInfo(
	String ticker,
	TradeType tradeType,
	Long orderCash,
	Long price,
	Integer leverage
) {
}
