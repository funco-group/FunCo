package com.found_404.funco.trade.dto.request;

import lombok.Builder;

@Builder
public record ReturnRateRequest(
	Long portfolioPirce,
	Long cash
) {
}
