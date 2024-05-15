package com.found_404.funco.feignClient.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "follow-service")
public interface FollowServiceClient {

	@GetMapping(value = "/api/v1/follows/investment")
	Map<Long, Long> getFollowerInvestmentList();

	@GetMapping("/following/investment")
	Map<Long, Long> getFollowingInvestmentList();
}
