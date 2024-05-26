package com.found_404.funco.feignClient.dto;

import lombok.Builder;

@Builder
public record HoldingCoinInfo(
	String ticker,
	Double volume,
	Double averagePrice
) {
}
