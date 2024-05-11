package com.found_404.funcomember.member.dto;

import com.found_404.funcomember.member.domain.type.MemberStatus;

import lombok.Builder;

@Builder
public record MemberDto(
	Long id,
	String oauthId,
	String nickname,
	String profileUrl,
	String introduction,
	Long cash,
	MemberStatus status

) {

}
