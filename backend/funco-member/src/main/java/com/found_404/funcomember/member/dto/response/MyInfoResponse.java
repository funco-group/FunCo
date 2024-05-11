package com.found_404.funcomember.member.dto.response;

import lombok.Builder;

@Builder
public record MyInfoResponse(
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
	Long badgeId
) {
}
