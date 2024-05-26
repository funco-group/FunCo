package com.found_404.funcomember.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PortfolioStatusRequest(
		@NotBlank
		String portfolioStatus,
		@NotNull
		Long portfolioPrice
) {
}
