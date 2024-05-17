package com.found_404.funcomember.feignClient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.found_404.funcomember.feignClient.dto.RankingResponse;

@FeignClient(name = "rank-service")
public interface RankServiceClient {

	@GetMapping("/api/v1/rank/{memberId}")
	RankingResponse getRankingInfo(@RequestParam String type, @PathVariable Long memberId);

	@GetMapping("/api/v1/hello")
	String hello();
}
