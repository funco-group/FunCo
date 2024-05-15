package com.found_404.funcomember.member.service;

import static com.found_404.funcomember.member.exception.MemberErrorCode.*;

import com.found_404.funcomember.feignClient.service.FollowService;
import com.found_404.funcomember.feignClient.service.TradeService;
import com.found_404.funcomember.member.dto.response.AssetResponse;
import com.found_404.funcomember.member.dto.response.SimpleMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funcomember.member.domain.Member;
import com.found_404.funcomember.member.domain.OauthId;
import com.found_404.funcomember.member.domain.repository.MemberRepository;
import com.found_404.funcomember.member.domain.type.MemberStatus;
import com.found_404.funcomember.member.domain.type.OauthServerType;
import com.found_404.funcomember.member.domain.type.PortfolioStatusType;
import com.found_404.funcomember.member.dto.request.OAuthMemberRequest;
import com.found_404.funcomember.member.dto.response.CashResponse;
import com.found_404.funcomember.member.dto.response.OAuthMemberResponse;
import com.found_404.funcomember.member.exception.MemberException;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final Long INIT_CASH = 10_000_000L;
	private final MemberRepository memberRepository;
	private final TradeService tradeService;
	private final FollowService followService;

	// private final RedisTemplate<String, Object> rankZSetRedisTemplate;
	//
	// @Transactional(readOnly = true)
	// public UserInfoResponse readMember(Long loginMemberId, Long memberId) {
	// 	List<HoldingCoinsDto> holdingCoinsDto = memberRepository.findHoldingCoinsByMemberId(memberId); // 보유 코인 정보
	// 	MemberInfo memberInfo = memberRepository.findUserInfoByMemberId(memberId);
	// 	ZSetOperations<String, Object> zSetOperations = rankZSetRedisTemplate.opsForZSet();
	//
	// 	return UserInfoResponse.builder()
	// 		.memberId(memberId)
	// 		.nickname(memberInfo.nickname())
	// 		.profileUrl(memberInfo.profileUrl())
	// 		.introduction(memberInfo.introduction())
	// 		.assetRank(getRankingByMemberId(memberId, ASSET, zSetOperations))
	// 		.followingCashRank(getRankingByMemberId(memberId, FOLLOWER_CASH, zSetOperations))
	// 		.memberAssetInfo(MemberAssetInfo.builder()
	// 			.cash(memberInfo.cash())
	// 			.coins(holdingCoinsDto)
	// 			.build())
	// 		.topCoins(memberRepository.findRecentTradedCoinByMemberId(memberId))
	// 		.followerCash(memberRepository.getFollowerCashByMemberId(memberId))
	// 		.followingCash(memberRepository.getFollowingCashByMemberId(memberId))
	// 		.isFollow(memberRepository.isFollowedByMemberId(loginMemberId, memberId))
	// 		.portfolioStatus(memberInfo.portfolioStatus())
	// 		.portfolioPrice(memberInfo.portfolioPrice())
	// 		.badgeId(memberRepository.findWearingBadgeByMemberId(memberId))
	// 		.build();
	// }
	//
	// @Transactional(readOnly = true)
	// public MyInfoResponse readMember(Long memberId) {
	// 	List<HoldingCoinsDto> holdingCoinsDto = memberRepository.findHoldingCoinsByMemberId(memberId); // 보유 코인 정보
	// 	MemberInfo memberInfo = memberRepository.findMyInfoByMemberId(memberId);
	// 	ZSetOperations<String, Object> zSetOperations = rankZSetRedisTemplate.opsForZSet();
	//
	// 	return MyInfoResponse.builder()
	// 		.memberId(memberId)
	// 		.nickname(memberInfo.nickname())
	// 		.profileUrl(memberInfo.profileUrl())
	// 		.introduction(memberInfo.introduction())
	// 		.assetRank(getRankingByMemberId(memberId, ASSET, zSetOperations))
	// 		.followingCashRank(getRankingByMemberId(memberId, FOLLOWER_CASH, zSetOperations))
	// 		.memberAssetInfo(MemberAssetInfo.builder()
	// 			.cash(memberInfo.cash())
	// 			.coins(holdingCoinsDto)
	// 			.build())
	// 		.topCoins(memberRepository.findRecentTradedCoinByMemberId(memberId))
	// 		.followerCash(memberRepository.getFollowerCashByMemberId(memberId))
	// 		.followingCash(memberRepository.getFollowingCashByMemberId(memberId))
	// 		.badgeId(memberRepository.findWearingBadgeByMemberId(memberId))
	// 		.build();
	// }
	//
	// private Long getRankingByMemberId(Long memberId, RankType rankType, ZSetOperations<String, Object> zSetOperations) {
	// 	Set<ZSetOperations.TypedTuple<Object>> typedTuples = zSetOperations.reverseRangeWithScores(rankType.toString(),
	// 		0, -1);
	//
	// 	AtomicInteger index = new AtomicInteger(0); // 인덱스를 저장할 AtomicInteger 생성
	// 	Optional<Long> result = typedTuples.stream()
	// 		.map(tuple -> (RankResponse)tuple.getValue())
	// 		.filter(Objects::nonNull)
	// 		.filter(rankResponse -> {
	// 			index.incrementAndGet(); // 인덱스 증가
	// 			return Objects.equals(rankResponse.member().id(), memberId); // 조건 확인
	// 		})
	// 		.findFirst()
	// 		.map(__ -> index.longValue());
	//
	// 	return result.orElse(null);
	// }

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

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
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
}
