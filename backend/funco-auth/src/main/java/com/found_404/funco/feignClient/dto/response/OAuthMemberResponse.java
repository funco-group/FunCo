package com.found_404.funco.feignClient.dto.response;

public record OAuthMemberResponse(
	Long memberId,
	String nickname,
	String profileUrl
	// Integer unReadCount
) {
}
