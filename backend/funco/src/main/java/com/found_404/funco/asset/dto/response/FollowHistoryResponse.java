package com.found_404.funco.asset.dto.response;

import java.time.LocalDateTime;

import com.found_404.funco.asset.domain.type.AssetTradeType;
import com.querydsl.core.annotations.QueryProjection;

public record FollowHistoryResponse(
	LocalDateTime settleDate,
	AssetTradeType assetTradeType,
	String followName,
	Long investment,
	Long settlement,
	Double returnRate,
	Long commision,
	LocalDateTime followDate
) implements AssetHistoryResponse {

	@QueryProjection
	public FollowHistoryResponse(LocalDateTime settleDate, AssetTradeType assetTradeType, String followName, Long investment, Long settlement,
		Double returnRate, Long commision, LocalDateTime followDate) {
		this.settleDate = settleDate;
		this.assetTradeType = assetTradeType;
		this.followName = followName;
		this.investment = investment;
		this.settlement = settlement;
		this.returnRate = returnRate;
		this.commision = commision;
		this.followDate = followDate;
	}

}
