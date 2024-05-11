package com.found_404.funcomember.auth.dto;

import com.found_404.funcomember.member.domain.Member;

import lombok.Builder;

@Builder
public record OauthDto(
	Member member,
	String accessToken
) {
	public static OauthDto from(Member member, String at) {
		return OauthDto.builder()
			.member(member)
			.accessToken(at)
			.build();
	}
}