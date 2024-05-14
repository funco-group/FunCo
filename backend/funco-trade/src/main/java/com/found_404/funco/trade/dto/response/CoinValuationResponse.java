package com.found_404.funco.trade.dto.response;

import java.util.Map;

public record CoinValuationResponse(
	Map<String, Double> coinValuation
) {
}
