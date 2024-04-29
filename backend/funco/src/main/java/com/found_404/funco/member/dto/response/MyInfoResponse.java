package com.found_404.funco.member.dto.response;

import java.util.List;

import com.found_404.funco.member.dto.MemberAssetInfo;
import com.found_404.funco.trade.dto.RecentTradedCoin;

import lombok.Builder;

@Builder
public record MyInfoResponse(
	Long memberId,
	String nickname,
	String profileUrl,
	String introduction,
	Long assetRank,
	Long followingCashRank,
	MemberAssetInfo memberAssetInfo,
	List<RecentTradedCoin> topCoins,
	Long followingCash,
	Long followerCash,
	Long badgeId
) {
}
