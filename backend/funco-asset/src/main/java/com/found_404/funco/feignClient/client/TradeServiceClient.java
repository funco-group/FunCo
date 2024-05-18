package com.found_404.funco.feignClient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "trade-service")
public interface TradeServiceClient {
	
	@DeleteMapping("/api/trade/coins")
	Void removeCoins(@RequestParam Long memberId);
}
