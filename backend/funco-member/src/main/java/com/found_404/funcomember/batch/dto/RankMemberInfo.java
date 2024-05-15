package com.found_404.funcomember.batch.dto;

import com.found_404.funcomember.member.domain.Member;

import lombok.Builder;

@Builder
public record RankMemberInfo(
	Long id,
	String nickname,
	String profileUrl,
	String introduction,
	Long cash,
	String portfolioStatus,
	Long portfolioPrice
) {
	public static RankMemberInfo from(Member member) {
		return RankMemberInfo.builder()
			.id(member.getId())
			.nickname(member.getNickname())
			.profileUrl(member.getProfileUrl())
			.introduction(member.getIntroduction())
			.cash(member.getCash())
			.portfolioStatus(member.getPortfolioStatus().name())
			.portfolioPrice(member.getPortfolioPrice())
			.build();
	}
}
