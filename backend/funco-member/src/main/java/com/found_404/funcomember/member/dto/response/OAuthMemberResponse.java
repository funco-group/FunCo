package com.found_404.funcomember.member.dto.response;

import com.found_404.funcomember.member.domain.Member;

import lombok.Builder;

@Builder
public record OAuthMemberResponse(
	Long memberId,
	String nickname,
	String profileUrl
	// Integer unReadCount
) {
	public static OAuthMemberResponse from(Member member) {
		return OAuthMemberResponse.builder()
			.memberId(member.getId())
			.nickname(member.getNickname())
			.profileUrl(member.getProfileUrl())
			.build();
	}
}
