package com.found_404.funcomember.feignClient.client;

import java.util.List;

import com.found_404.funcomember.feignClient.dto.FollowAssetResponse;
import com.found_404.funcomember.feignClient.dto.FollowStatusResponse;
import com.found_404.funcomember.feignClient.dto.FollowerInfoResponse;
import com.found_404.funcomember.feignClient.dto.InvestmentsResponse;
import com.found_404.funcomember.portfolio.dto.request.FollowerProfitRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "follow-service")
public interface FollowServiceClient {

	@GetMapping("/api/v1/follows/investments")
	InvestmentsResponse getInvestments(@RequestParam Long memberId);

	@GetMapping("/api/v1/hello")
	String hello();

    @GetMapping("/api/v1/follows/{followingId}/followers")
    List<FollowerInfoResponse> getFollowerInfos(@PathVariable Long followingId);

    @PatchMapping("/api/v1/follows/{followingId}/followers")
    void modifyFollower(@PathVariable Long followingId, @RequestBody FollowerProfitRequest followerProfitRequest);

	@GetMapping("/api/v1/follows/{loginMemberId}/following/{targetMemberId}")
	FollowStatusResponse getFollowStatus(@PathVariable Long loginMemberId, @PathVariable Long targetMemberId);

	@GetMapping("/api/v1/follows/asset")
	FollowAssetResponse getFollowAsset(@RequestParam Long memberId);
}
