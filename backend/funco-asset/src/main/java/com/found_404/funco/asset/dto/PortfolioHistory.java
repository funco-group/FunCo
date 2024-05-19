package com.found_404.funco.asset.dto;

import java.time.LocalDateTime;

import com.found_404.funco.asset.domain.type.AssetTradeType;
import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
import com.querydsl.core.annotations.QueryProjection;

public record PortfolioHistory(
	LocalDateTime date,
	String portfolioName,
	AssetTradeType assetTradeType,
	Double price,
	Long endingCash
) implements AssetHistoryResponse {

	@QueryProjection
	public PortfolioHistory(LocalDateTime date, String portfolioName, AssetTradeType assetTradeType, Double price,
							Long endingCash) {
		this.date = date;
		this.portfolioName = portfolioName;
		this.assetTradeType = assetTradeType;
		this.price = price;
		this.endingCash = endingCash;
	}
}
