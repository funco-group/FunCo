package com.found_404.funcomember.member.dto.response;

import com.found_404.funcomember.member.domain.type.PortfolioStatusType;
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

	PortfolioStatusType portfolioStatus,
	Long portfolioPrice
) {
}
