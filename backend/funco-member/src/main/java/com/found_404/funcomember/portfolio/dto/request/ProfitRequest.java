package com.found_404.funcomember.portfolio.dto.request;

import lombok.Builder;

@Builder
public record ProfitRequest(
	Long cash,
	Double ratio
) {
}
