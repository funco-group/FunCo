package com.found_404.funco.feignClient.dto.request;

import com.found_404.funco.auth.dto.OauthId;

import lombok.Builder;

@Builder
public record OAuthMemberRequest(
	OauthId oauthId,
	String nickname,
	String profileUrl
) {
}
