package com.found_404.funco.feignClient.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.found_404.funco.rank.dto.HoldingCoinInfo;

@FeignClient(name = "trade-service")
public interface TradeServiceClient {
	@GetMapping(value = "/api/v1/crypto")
	Map<String, Long> getCryptoPrice(@RequestParam List<String> tickers);

	@GetMapping("/api/v1/batch/holdings")
	List<HoldingCoinInfo> getHoldingCoinInfoList();

	@GetMapping(value = "/api/v1/batch/holdings/ticker")
	List<String> getHoldingCoinList();

	@GetMapping("/api/v1/batch/opentrades")
	Map<Long, Long> getOpenTradeOrderCashList();
}
