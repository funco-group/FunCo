package com.found_404.funco.asset.dto.response;

import java.time.LocalDateTime;

import com.found_404.funco.asset.domain.type.TradeType;
import com.querydsl.core.annotations.QueryProjection;

public record PortfolioHistoryResponse(
	LocalDateTime date,
	String portfolioName,
	TradeType tradeType,
	Long price,
	Long endingCash
) implements AssetHistoryResponse {

	@QueryProjection
	public PortfolioHistoryResponse(LocalDateTime date, String portfolioName, TradeType tradeType, Long price,
		Long endingCash) {
		this.date = date;
		this.portfolioName = portfolioName;
		this.tradeType = tradeType;
		this.price = price;
		this.endingCash = endingCash;
	}
}
