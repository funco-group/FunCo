package com.found_404.funco.trade.dto.request;

import lombok.Builder;

@Builder
public record ProfitRequest(
	Long cash,
	Double ratio
) {
}
