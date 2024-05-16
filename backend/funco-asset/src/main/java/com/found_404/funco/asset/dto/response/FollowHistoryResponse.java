package com.found_404.funco.asset.dto.response;

import java.time.LocalDateTime;

import com.found_404.funco.asset.domain.type.TradeType;
import com.querydsl.core.annotations.QueryProjection;

public record FollowHistoryResponse(
	LocalDateTime settleDate,
	TradeType tradeType,
	String followName,
	Long investment,
	Long settlement,
	Double returnRate,
	Long commission,
	LocalDateTime followDate
) implements AssetHistoryResponse {

	@QueryProjection
	public FollowHistoryResponse(LocalDateTime settleDate, TradeType tradeType, String followName, Long investment, Long settlement,
								 Double returnRate, Long commission, LocalDateTime followDate) {
		this.settleDate = settleDate;
		this.tradeType = tradeType;
		this.followName = followName;
		this.investment = investment;
		this.settlement = settlement;
		this.returnRate = returnRate;
		this.commission = commission;
		this.followDate = followDate;
	}

}
