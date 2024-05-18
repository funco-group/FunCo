package com.found_404.funcomember.member.service;

import static com.found_404.funcomember.member.domain.type.RankType.*;
import static com.found_404.funcomember.member.exception.MemberErrorCode.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funcomember.feignClient.dto.FollowAssetResponse;
import com.found_404.funcomember.feignClient.dto.HoldingCoinResponse;
import com.found_404.funcomember.feignClient.dto.MemberInitCashDate;
import com.found_404.funcomember.feignClient.dto.OAuthIdResponse;
import com.found_404.funcomember.feignClient.service.FollowService;
import com.found_404.funcomember.feignClient.service.RankService;
import com.found_404.funcomember.feignClient.service.TradeService;
import com.found_404.funcomember.member.domain.Member;
import com.found_404.funcomember.member.domain.OauthId;
import com.found_404.funcomember.member.domain.repository.MemberRepository;
import com.found_404.funcomember.member.domain.type.MemberStatus;
import com.found_404.funcomember.member.domain.type.OauthServerType;
import com.found_404.funcomember.member.domain.type.PortfolioStatusType;
import com.found_404.funcomember.member.dto.MemberAssetInfo;
import com.found_404.funcomember.member.dto.MemberInfo;
import com.found_404.funcomember.member.dto.request.OAuthMemberRequest;
import com.found_404.funcomember.member.dto.response.AssetResponse;
import com.found_404.funcomember.member.dto.response.CashResponse;
import com.found_404.funcomember.member.dto.response.MyInfoResponse;
import com.found_404.funcomember.member.dto.response.OAuthMemberResponse;
import com.found_404.funcomember.member.dto.response.SimpleMember;
import com.found_404.funcomember.member.exception.MemberException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final Long INIT_CASH = 10_000_000L;
	private final MemberRepository memberRepository;
	private final TradeService tradeService;
	private final FollowService followService;
	private final RankService rankService;

	public MyInfoResponse getMyPage(Long memberId) {
		List<HoldingCoinResponse> holdingCoinResponses = tradeService.getHoldingCoinInfos(memberId); // 보유 코인 정보
		MemberInfo memberInfo = memberRepository.findMyInfoByMemberId(memberId);
		FollowAssetResponse followAssetResponse = followService.getFollowAsset(memberId);

		return MyInfoResponse.builder()
			.memberId(memberId)
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
			.followerCash(followAssetResponse.followerCash())
			.followingCash(followAssetResponse.followingCash())
			.portfolioPrice(memberInfo.portfolioPrice())
			.portfolioStatus(memberInfo.portfolioStatus())
			.build();
	}

	@Transactional
	public void updateNickname(Long loginMemberId, String nickname) {
		getMember(loginMemberId).updateNickname(nickname);
	}

	@Transactional
	public void updateIntroduce(Long loginMemberId, String introduction) {
		getMember(loginMemberId).updateIntroduction(introduction);
	}

	@Transactional
	public void withdraw(Long loginMemberId) {
		getMember(loginMemberId).withdraw();
	}

	@Transactional
	public void updateCash(Long memberId, Long updateCash) {
		Member member = getMember(memberId);
		member.updateCash(updateCash);
	}

	public CashResponse getCash(Long memberId) {
		return new CashResponse(getMember(memberId).getCash());
	}

	public OAuthMemberResponse readAuthMember(String provider, String oauthId) {
		return OAuthMemberResponse.from(
			memberRepository.findByOauthId(new OauthId(oauthId, OauthServerType.fromName(provider)))
				.orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER)));
	}

	public OAuthMemberResponse createAuthMember(OAuthMemberRequest oauthMemberRequest) {
		Member member = memberRepository.save(Member.builder()
			.oauthId(oauthMemberRequest.oauthId())
			.nickname(oauthMemberRequest.nickname())
			.profileUrl(oauthMemberRequest.profileUrl())
			.cash(INIT_CASH)
			.status(MemberStatus.NORMAL)
			.portfolioStatus(PortfolioStatusType.PUBLIC)
			.build());
		return OAuthMemberResponse.from(member);
	}

	public MemberInitCashDate readInitCashDate(Long memberId) {
		return new MemberInitCashDate(getMember(memberId).getInitCashDate());
	}

	public OAuthIdResponse readOAuthId(Long memberId) {
		return new OAuthIdResponse(getMember(memberId).getOauthId().getOauthServerId());
	}

	/*
	 * 	총자산 : 현금 + 코인 + 미체결 금액 + 팔로우 투자금 + ?(선물투자금)
	 * */
	public AssetResponse getTotalAsset(Long memberId) {
		long totalAsset = getMember(memberId).getCash();

		// [API SELECT]
		totalAsset += tradeService.getCoinValuations(memberId).totalTradeAsset();

		// [API SELECT]
		totalAsset += followService.getInvestments(memberId);

		return new AssetResponse(totalAsset);
	}

	@Transactional
	public void modifyCashAndInitCashDate(Long memberId) {
		Member member = getMember(memberId);

		member.updateInitCash();
		member.updateInitCashDate(LocalDateTime.now());
	}

	public List<SimpleMember> getMembers(List<Long> ids) {
		return memberRepository.findAllByIdIn(ids)
			.stream()
			.map(member -> SimpleMember.builder()
				.id(member.getId())
				.nickname(member.getNickname())
				.profileUrl(member.getProfileUrl())
				.build())
			.toList();
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
	}
}
