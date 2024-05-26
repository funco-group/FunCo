package com.found_404.funco.feignClient.service;

import static com.found_404.funco.rank.exception.RankErrorCode.*;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.found_404.funco.feignClient.client.TradeServiceClient;
import com.found_404.funco.rank.dto.HoldingCoinInfo;
import com.found_404.funco.rank.exception.RankException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TradeService {

	private final TradeServiceClient tradeServiceClient;

	public Map<String, Double> getCryptoPrice(List<String> tickers) {
		try {
			return tradeServiceClient.getCryptoPrice(tickers);
		} catch (
			FeignException e) {
			log.error("trade client error : {}", e.getMessage());
			throw new RankException(RANK_NOT_FOUND);
		}
	}

	public List<HoldingCoinInfo> getHoldingCoinInfoList() {
		try {
			return tradeServiceClient.getHoldingCoinInfoList();
		} catch (FeignException e) {
			log.error("trade client error : {}", e.getMessage());
			throw new RankException(RANK_NOT_FOUND);
		}
	}

	public List<String> getHoldingCoinList() {
		try {
			return tradeServiceClient.getHoldingCoinList();
		} catch (FeignException e) {
			log.error("trade client error : {}", e.getMessage());
			throw new RankException(RANK_NOT_FOUND);
		}
	}

	public Map<Long, Long> getOpenTradeOrderCashList() {
		try {
			return tradeServiceClient.getOpenTradeOrderCashList();
		} catch (FeignException e) {
			log.error("trade client error : {}", e.getMessage());
			throw new RankException(RANK_NOT_FOUND);
		}
	}
}
