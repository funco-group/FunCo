package com.found_404.funco.feignClient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.found_404.funco.feignClient.dto.request.OAuthMemberRequest;
import com.found_404.funco.feignClient.dto.response.OAuthMemberResponse;

@FeignClient(name = "member-service")
public interface MemberServiceClient {
	@GetMapping(value = "/api/v1/members/auth/{provider}/{oauthId}")
	OAuthMemberResponse getAuthMember(@PathVariable String provider,
		@PathVariable String oauthId);

	@PostMapping(value = "/api/v1/members")
	OAuthMemberResponse addAuthMember(@RequestBody OAuthMemberRequest oauthMemberRequest);
}
