package com.found_404.funco.feignClient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "follow-service")
public interface FollowServiceClient {

	@PatchMapping("/api/v1/follows/asset")
	Void modifyFollowingAndFollower(@RequestParam Long memberId);
}
