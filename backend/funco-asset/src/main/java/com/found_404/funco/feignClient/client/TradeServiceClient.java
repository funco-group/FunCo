package com.found_404.funco.feignClient.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.found_404.funco.asset.dto.ActiveFutureInfo;
import com.found_404.funco.asset.dto.HoldingCoinInfo;

@FeignClient(name = "trade-service")
public interface TradeServiceClient {

	@DeleteMapping("/api/trade/coins")
	Void removeCoins(@RequestParam Long memberId);

	@GetMapping("/api/trade/asset/holding")
	List<HoldingCoinInfo> getAssetHoldingCoin(@RequestParam Long memberId);

	@GetMapping("/api/trade/asset/future")
	List<ActiveFutureInfo> getAssetFuture(@RequestParam Long memberId);
}
