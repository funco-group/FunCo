package com.found_404.funco.asset.dto;

import java.time.LocalDateTime;

import com.found_404.funco.asset.domain.type.AssetTradeType;
import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
import com.querydsl.core.annotations.QueryProjection;

public record FollowHistory(
	LocalDateTime settleDate,
	AssetTradeType assetTradeType,
	Long investment,
	Long settlement,
	Double returnRate,
	Long commission,
	LocalDateTime followDate
) implements AssetHistoryResponse {

	@QueryProjection
	public FollowHistory(LocalDateTime settleDate, AssetTradeType assetTradeType, Long investment, Long settlement,
						 Double returnRate, Long commission, LocalDateTime followDate) {
		this.settleDate = settleDate;
		this.assetTradeType = assetTradeType;
		this.investment = investment;
		this.settlement = settlement;
		this.returnRate = returnRate;
		this.commission = commission;
		this.followDate = followDate;
	}

}
