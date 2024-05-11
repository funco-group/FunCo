package com.found_404.funcomember.member.dto.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
	Long memberId,
	String nickname,
	String profileUrl,
	String introduction,
	Long assetRank,
	Long followingCashRank,
	// MemberAssetInfo memberAssetInfo,
	// List<RecentTradedCoin> topCoins,
	Long followingCash,
	Long followerCash,
	Boolean isFollow,
	String portfolioStatus,
	Long portfolioPrice,
	Long badgeId
) {
}
