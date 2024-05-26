package com.found_404.funco.rank.dto.response;

import lombok.Builder;

@Builder
public record RankingResponse(
	Long rank
) {
}