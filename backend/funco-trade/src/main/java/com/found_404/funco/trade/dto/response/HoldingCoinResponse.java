package com.found_404.funco.trade.dto.response;

import lombok.Builder;

@Builder
public record HoldingCoinResponse(
	String ticker,
	Double volume
) {
}
