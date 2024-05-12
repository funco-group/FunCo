package com.found_404.funcomember.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funcomember.member.domain.Member;
import com.found_404.funcomember.member.domain.repository.MemberRepository;
import com.found_404.funcomember.member.exception.MemberErrorCode;
import com.found_404.funcomember.member.exception.MemberException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
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

	private Member getMember(Long loginMemberId) {
		return memberRepository.findById(loginMemberId)
			.orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
	}

	public void updateCash(Long memberId, Long updateCash) {
		Member member = getMember(memberId);
		member.updateCash(updateCash);
	}
}
