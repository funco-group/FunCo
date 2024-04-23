package com.found_404.funco.asset.domain.type;

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
	PURCHASE_PORTFOLIO("포트폴리오구매");

	private final String korean;
}
