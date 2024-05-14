package com.found_404.funco.auth.dto;

import com.found_404.funco.auth.dto.type.MemberStatus;
import com.found_404.funco.auth.dto.type.PortfolioStatusType;

import lombok.Builder;

@Builder
public record MemberDto(
	Long memberId,
	OauthId oauthId,
	String nickname,
	String profileUrl,
	String introduction,
	Long cash,
	MemberStatus status,
	PortfolioStatusType portfolioStatus,
	Long portfolioPrice
) {
}
