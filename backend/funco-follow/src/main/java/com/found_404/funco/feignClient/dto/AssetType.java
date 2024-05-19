package com.found_404.funco.feignClient.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetType {
	COIN,
	FOLLOW,
	PORTFOLIO,
	FUTURES

}