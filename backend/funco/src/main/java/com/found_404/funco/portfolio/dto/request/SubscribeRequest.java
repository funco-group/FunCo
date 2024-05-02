package com.found_404.funco.portfolio.dto.request;

import lombok.Builder;

@Builder
public record SubscribeRequest(
	Long memberId
) {
}
