package com.found_404.funco.client.dto;

import java.util.List;

public record CoinValuationResponse(
	List<CoinValuation> coinValuations,
	Long totalTradeAsset
) {
}
