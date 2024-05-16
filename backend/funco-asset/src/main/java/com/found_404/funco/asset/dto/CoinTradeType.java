package com.found_404.funco.asset.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoinTradeType {
	BUY("매수"),
	SELL("매도"),
	LONG("롱"),
	SHORT("숏");

	private final String korean;
}
