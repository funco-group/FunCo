package com.found_404.funco.asset.domain.type;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetTradeType {
	ALL("전체"),
	BUY("매수"),
	SELL("매도"),
	LONG("롱"),
	SHORT("숏"),
	FOLLOWING("팔로잉"),
	FOLLOWER("팔로워"),
	PURCHASE_PORTFOLIO("포트폴리오구매"),
	SELL_PORTFOLIO("포트폴리오판매");

	private final String korean;

	// 특정 값이 미리 지정해 둔 TradeType에 포함되어 있는 지 여부
	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(type -> type.name().equals(value));
	}

}
