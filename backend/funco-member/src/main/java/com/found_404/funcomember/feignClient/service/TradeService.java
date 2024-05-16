package com.found_404.funcomember.feignClient.service;

import com.found_404.funcomember.feignClient.client.TradeServiceClient;
import com.found_404.funcomember.feignClient.dto.CoinValuationResponse;
import com.found_404.funcomember.member.exception.MemberException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.found_404.funcomember.member.exception.MemberErrorCode.TRADE_SERVER_ERROR;

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
			throw new MemberException(TRADE_SERVER_ERROR);
		}
	}

	public Map<String, Long> getCryptoPrice(List<String> tickers) {
		try {
			return tradeServiceClient.getCryptoPrice(tickers);
		} catch (FeignException e) {
			log.error("{} Crypto price error : {}", SERVER_NAME, e.getMessage());
			throw new MemberException(TRADE_SERVER_ERROR);
		}
	}
}
