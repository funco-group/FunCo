package com.found_404.funco.feignClient.service;

import static com.found_404.funco.asset.exception.AssetErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.found_404.funco.asset.exception.AssetException;
import com.found_404.funco.feignClient.client.TradeServiceClient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TradeService {
	private final TradeServiceClient tradeServiceClient;
	private final String SERVER_NAME = "[trade-service]";

	public void removeCoins(@RequestParam Long memberId) {
		try {
			tradeServiceClient.removeCoins(memberId);
		} catch (FeignException e) {
			log.error("{} Delete Coins error : {}", SERVER_NAME, e.getMessage());
			throw new AssetException(TRADE_SERVER_ERROR);
		}
	}
}
