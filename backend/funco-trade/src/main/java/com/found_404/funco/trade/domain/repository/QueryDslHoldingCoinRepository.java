package com.found_404.funco.trade.domain.repository;

import java.util.List;

import com.found_404.funco.trade.dto.HoldingCoinInfo;

public interface QueryDslHoldingCoinRepository {
	List<String> findHoldingCoin();

	List<HoldingCoinInfo> findHoldingCoinInfoList();
}
