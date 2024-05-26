package com.found_404.funco.batch.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.found_404.funco.trade.domain.repository.HoldingCoinRepository;
import com.found_404.funco.trade.domain.repository.OpenTradeRepository;
import com.found_404.funco.trade.dto.HoldingCoinInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchAllService {

	private final HoldingCoinRepository holdingCoinRepository;
	private final OpenTradeRepository openTradeRepository;

	public List<String> readHoldingCoinList() {
		return holdingCoinRepository.findHoldingCoin();
	}

	public List<HoldingCoinInfo> readHoldingCoinInfoList() {
		return holdingCoinRepository.findHoldingCoinInfoList();
	}

	public Map<Long, Long> readOpenTradeOrderCashList() {
		return openTradeRepository.findAllMemberIdToOrderCash();
	}
}
