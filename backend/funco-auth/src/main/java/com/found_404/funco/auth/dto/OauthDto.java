package com.found_404.funco.auth.dto;

import lombok.Builder;

@Builder
public record OauthDto(
	MemberDto memberDto,
	String accessToken
) {
	public static OauthDto from(MemberDto memberDto, String at) {
		return OauthDto.builder()
			.memberDto(memberDto)
			.accessToken(at)
			.build();
	}
}