package com.found_404.funco.asset.dto;

import com.found_404.funco.asset.domain.type.AssetTradeType;

import lombok.Builder;

@Builder
public record ActiveFutureInfo(
	String ticker,
	AssetTradeType assetTradeType,
	Long orderCash,
	Double price,
	Integer leverage
) {
}
