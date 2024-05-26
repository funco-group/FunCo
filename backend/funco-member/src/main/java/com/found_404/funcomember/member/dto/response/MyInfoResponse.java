package com.found_404.funcomember.member.dto.response;

import java.util.List;

import com.found_404.funcomember.feignClient.dto.RecentTradedCoin;
import com.found_404.funcomember.member.domain.type.PortfolioStatusType;
import com.found_404.funcomember.member.dto.MemberAssetInfo;

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
	String portfolioStatus,
	Long portfolioPrice
) {
}
