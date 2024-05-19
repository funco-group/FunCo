package com.found_404.funco.feignClient.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum AssetTradeType {
	ALL("전체"),
	FOLLOWING("팔로잉"),
	FOLLOWER("팔로워")
	;

	private final String korean;

	// 특정 값이 미리 지정해 둔 TradeType에 포함되어 있는 지 여부
	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(type -> type.name().equals(value));
	}

}
