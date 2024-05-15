package com.found_404.funcomember.member.domain.repository.impl;

import org.springframework.stereotype.Repository;

import com.found_404.funcomember.member.domain.repository.MemberCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private static final int RECENT_TRADED_COIN_SIZE = 3;

	// @Override
	// public List<HoldingCoinsDto> findHoldingCoinsByMemberId(Long memberId) {
	// 	return jpaQueryFactory
	// 		.select(new QHoldingCoinsDto(holdingCoin.ticker, holdingCoin.volume))
	// 		.from(holdingCoin)
	// 		.where(holdingCoin.member.id.eq(memberId))
	// 		.fetch();
	// }
	//
	// @Override
	// public MemberInfo findMyInfoByMemberId(Long memberId) {
	// 	return jpaQueryFactory
	// 		.select(new QMemberInfo(member.nickname, member.profileUrl, member.introduction, member.cash))
	// 		.from(member)
	// 		.where(member.id.eq(memberId))
	// 		.fetchFirst();
	// }
	//
	// public MemberInfo findUserInfoByMemberId(Long memberId) {
	// 	CaseBuilder caseBuilder = new CaseBuilder();
	// 	return jpaQueryFactory
	// 		.select(new QMemberInfo(member.id, member.nickname, member.profileUrl, member.introduction, member.cash,
	// 			caseBuilder.when(member.portfolioStatus.stringValue().eq(PortfolioStatusType.PUBLIC.toString()))
	// 				.then(PUBLIC.getValue())
	// 				.when(member.portfolioStatus.stringValue()
	// 					.eq(PortfolioStatusType.PRIVATE.toString())
	// 					.and(subscribe.id.isNotNull())
	// 					.and(subscribe.expiredAt.after(
	// 						LocalDateTime.now())))
	// 				.then(SUBSCRIBE.getValue())
	// 				.otherwise(PRIVATE.getValue())
	// 				.as("portfolioStatus"), member.portfolioPrice))
	// 		.from(subscribe)
	// 		.rightJoin(member).on(subscribe.toMember.id.eq(member.id))
	// 		.where(member.id.eq(memberId))
	// 		.fetchFirst();
	// }
	//
	// @Override
	// public Long getFollowingCashByMemberId(Long memberId) {
	// 	return jpaQueryFactory.select(follow.investment.sum().coalesce(0L))
	// 		.from(follow)
	// 		.where(follow.follower.id.eq(memberId),
	// 			follow.settled.isNull().or(follow.settled.isFalse()))
	// 		.fetchFirst();
	// }
	//
	// @Override
	// public Long getFollowerCashByMemberId(Long memberId) {
	// 	return jpaQueryFactory.select(follow.investment.sum().coalesce(0L))
	// 		.from(follow)
	// 		.where(follow.following.id.eq(memberId),
	// 			follow.settled.isNull().or(follow.settled.isFalse()))
	// 		.fetchFirst();
	// }
	//
	// @Override
	// public Boolean isFollowedByMemberId(Long loginId, Long memberId) {
	// 	return !jpaQueryFactory
	// 		.from(follow)
	// 		.where(follow.follower.id.eq(loginId),
	// 			follow.following.id.eq(memberId),
	// 			follow.settled.isNull().or(follow.settled.isFalse()))
	// 		.fetch().isEmpty();
	// }
	//
	// @Override
	// public List<RecentTradedCoin> findRecentTradedCoinByMemberId(Long memberId) {
	// 	return jpaQueryFactory
	// 		.select(new QRecentTradedCoin(trade.ticker, trade.createdAt))
	// 		.from(trade)
	// 		.where(trade.member.id.eq(memberId))
	// 		.groupBy(trade.ticker)
	// 		.orderBy(trade.createdAt.desc())
	// 		.limit(RECENT_TRADED_COIN_SIZE)
	// 		.fetch();
	// }
	//
	// @Override
	// public Long findWearingBadgeByMemberId(Long memberId) {
	// 	return jpaQueryFactory
	// 		.select(wearingBadge.id)
	// 		.from(wearingBadge)
	// 		.where(wearingBadge.id.eq(memberId))
	// 		.fetchFirst();
	// }
}