package com.found_404.funcomember.member.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record MemberInfo(
	Long id,
	String nickname,
	String profileUrl,
	String introduction,
	Long cash,
	String portfolioStatus,
	Long portfolioPrice
) {
	@QueryProjection
	public MemberInfo(String nickname, String profileUrl, String introduction, Long cash, String portfolioStatus,
		Long portfolioPrice) {
		this(null, nickname, profileUrl, introduction, cash, portfolioStatus, portfolioPrice);
	}

	@QueryProjection
	public MemberInfo(Long id, String nickname, String profileUrl, String introduction, Long cash,
		String portfolioStatus,
		Long portfolioPrice) {
		this.id = id;
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.introduction = introduction;
		this.cash = cash;
		this.portfolioStatus = portfolioStatus;
		this.portfolioPrice = portfolioPrice;
	}
}
