package com.found_404.funco.rank.dto;

import lombok.Builder;

@Builder
public record HoldingCoinInfo(
	Long memberId,
	String ticker,
	Double volume
) {

}
