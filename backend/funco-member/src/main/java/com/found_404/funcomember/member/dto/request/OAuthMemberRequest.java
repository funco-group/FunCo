package com.found_404.funcomember.member.dto.request;

import com.found_404.funcomember.member.domain.OauthId;

public record OAuthMemberRequest(
	OauthId oauthId,
	String nickname,
	String profileUrl
) {
}
