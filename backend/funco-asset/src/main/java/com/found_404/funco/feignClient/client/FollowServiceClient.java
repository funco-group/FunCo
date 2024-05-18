package com.found_404.funco.feignClient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.found_404.funco.feignClient.dto.InvestmentsResponse;

@FeignClient(name = "follow-service")
public interface FollowServiceClient {

	@PatchMapping("/api/v1/follows/asset")
	Void modifyFollowingAndFollower(@RequestParam Long memberId);

	@GetMapping("/api/v1/follows/following/investments")
	InvestmentsResponse getFollowingInvestment(@RequestParam Long memberId);
}
