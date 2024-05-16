package com.found_404.funco.asset.dto.response;

import java.time.LocalDateTime;

import com.found_404.funco.asset.domain.type.AssetTradeType;
import com.querydsl.core.annotations.QueryProjection;

public record PortfolioHistoryResponse(
	LocalDateTime date,
	String portfolioName,
	AssetTradeType assetTradeType,
	Long price,
	Long endingCash
) implements AssetHistoryResponse {

	@QueryProjection
	public PortfolioHistoryResponse(LocalDateTime date, String portfolioName, AssetTradeType assetTradeType, Long price,
		Long endingCash) {
		this.date = date;
		this.portfolioName = portfolioName;
		this.assetTradeType = assetTradeType;
		this.price = price;
		this.endingCash = endingCash;
	}
}
