package com.found_404.funcomember.feignClient.dto;

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