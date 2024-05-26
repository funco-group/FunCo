package com.found_404.funco.trade.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record CoinValuationResponse(
	List<CoinValuation> coinValuations,
	Long totalTradeAsset
) {
}
