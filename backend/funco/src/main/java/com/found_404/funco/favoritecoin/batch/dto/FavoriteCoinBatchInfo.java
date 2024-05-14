package com.found_404.funco.favoritecoin.batch.dto;

import java.util.Set;

import lombok.Builder;

@Builder
public record FavoriteCoinBatchInfo(
	Long memberId,
	Set<String> tickers
) {
}
