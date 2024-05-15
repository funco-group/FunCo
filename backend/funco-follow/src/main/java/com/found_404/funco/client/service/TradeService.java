package com.found_404.funco.client.service;

import static com.found_404.funco.follow.exception.FollowErrorCode.*;

import org.springframework.stereotype.Service;

import com.found_404.funco.client.TradeServiceClient;
import com.found_404.funco.client.dto.CoinValuationResponse;
import com.found_404.funco.follow.exception.FollowException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class TradeService {
	private final TradeServiceClient tradeServiceClient;
	private final String SERVER_NAME = "[trade-service]";

	public CoinValuationResponse getCoinValuations(Long memberId) {
		try {
			return tradeServiceClient.getCoinValuations(memberId);
		} catch (FeignException e) {
			log.error("{} updateMemberCash error : {}", SERVER_NAME, e.getMessage());
			throw new FollowException(TRADE_SERVER_ERROR);
		}
	}

	public Map<String, Long> getCryptoPrice(List<String> tickers) {
		try {
			return tradeServiceClient.getCryptoPrice(tickers);
		} catch (FeignException e) {
			log.error("{} Crypto price error : {}", SERVER_NAME, e.getMessage());
			throw new FollowException(TRADE_SERVER_ERROR);
		}
	}
}
