package com.found_404.funcomember.feignClient.dto;

import java.util.List;

public record CoinValuationResponse(
	List<CoinValuation> coinValuations,
	Long totalTradeAsset
) {
}
