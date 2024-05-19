package com.found_404.funcomember.member.service;

import static com.found_404.funcomember.member.domain.type.RankType.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.found_404.funcomember.feignClient.dto.HoldingCoinResponse;
import com.found_404.funcomember.feignClient.service.FollowService;
import com.found_404.funcomember.feignClient.service.RankService;
import com.found_404.funcomember.feignClient.service.TradeService;
import com.found_404.funcomember.member.domain.repository.MemberRepository;
import com.found_404.funcomember.member.dto.MemberAssetInfo;
import com.found_404.funcomember.member.dto.MemberInfo;
import com.found_404.funcomember.member.dto.response.UserInfoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {
	private final MemberRepository memberRepository;
	private final TradeService tradeService;
	private final FollowService followService;
	private final RankService rankService;

	public UserInfoResponse getMyPage(Long loginMemberId, Long memberId) {
		List<HoldingCoinResponse> holdingCoinResponses = tradeService.getHoldingCoinInfos(memberId); // 보유 코인 정보
		MemberInfo memberInfo = memberRepository.findUserInfoByMemberId(loginMemberId, memberId);

		return UserInfoResponse.builder()
			.memberId(memberInfo.id())
			.nickname(memberInfo.nickname())
			.profileUrl(memberInfo.profileUrl())
			.introduction(memberInfo.introduction())
			.assetRank(rankService.getRankingInfo(ASSET.getDescription(), memberId).rank())
			.followingCashRank(rankService.getRankingInfo(FOLLOWER_CASH.getDescription(), memberId).rank())
			.memberAssetInfo(MemberAssetInfo.builder()
				.cash(memberInfo.cash())
				.coins(holdingCoinResponses)
				.build())
			.topCoins(tradeService.getRecentTradedCoins(memberId))
			.followerCash(followService.getFollowAsset(memberId).followerCash())
			.followingCash(followService.getFollowAsset(memberId).followingCash())
			.isFollow(followService.getFollowStatus(loginMemberId, memberId))
			.portfolioPrice(memberInfo.portfolioPrice())
			.portfolioStatus(memberInfo.portfolioStatus())
			.build();
	}
}
