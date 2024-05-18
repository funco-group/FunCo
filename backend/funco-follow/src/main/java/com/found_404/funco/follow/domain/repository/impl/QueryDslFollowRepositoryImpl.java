package com.found_404.funco.follow.domain.repository.impl;

import static com.found_404.funco.follow.domain.QFollow.*;
import static com.found_404.funco.follow.domain.QFollowingCoin.*;
import static com.found_404.funco.follow.exception.FollowErrorCode.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.found_404.funco.follow.domain.Follow;
import com.found_404.funco.follow.domain.repository.QueryDslFollowRepository;
import com.found_404.funco.follow.domain.type.SettleType;
import com.found_404.funco.follow.dto.*;
import com.found_404.funco.follow.dto.CoinInfo;
import com.found_404.funco.follow.dto.FollowingInfo;
import com.found_404.funco.follow.dto.QueryFollowingInfoResult;
import com.found_404.funco.follow.dto.SliceFollowingInfo;
import com.found_404.funco.follow.dto.response.FollowerInfoResponse;
import com.found_404.funco.follow.dto.response.QFollowerInfoResponse;
import com.found_404.funco.follow.exception.FollowException;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QueryDslFollowRepositoryImpl implements QueryDslFollowRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public SliceFollowingInfo findFollowingInfoListByMemberId(Long memberId, Long lastFollowId, int pageSize) {
		List<QueryFollowingInfoResult> queryFollowingInfoResult = jpaQueryFactory
			.select(Projections.constructor(
				QueryFollowingInfoResult.class, follow.id, follow.followingMemberId,
				follow.investment, follow.createdAt, follow.cash))
			.from(follow)
			.where(follow.followerMemberId.eq(memberId), follow.settled.eq(false), ltFollowId(lastFollowId))
			.orderBy(follow.id.desc())
			.limit(pageSize + 1)
			.fetch();

		List<Long> followIdList = queryFollowingInfoResult.stream().map(
			QueryFollowingInfoResult::followId
		).collect(Collectors.toList());

		boolean last = checkLastPage(followIdList, pageSize);

		Map<Long, List<CoinInfo>> queryFollowingCoinInfoResult = jpaQueryFactory
			.from(followingCoin)
			.where(followingCoin.follow.id.in(followIdList))
			.transform(groupBy(follow.id).as(list(Projections.constructor(CoinInfo.class,
				followingCoin.ticker,
				followingCoin.volume))));

		List<FollowingInfo> followingInfoList = queryFollowingInfoResult.stream()
			.map(result -> FollowingInfo.builder()
				.followId(result.followId())
				.followingId(result.followingId())
				.investment(result.investment())
				.followedAt(result.followedAt())
				.cash(result.cash())
				.coins(queryFollowingCoinInfoResult.getOrDefault(result.followId(), Collections.EMPTY_LIST))
				.build())
			.toList();

		return SliceFollowingInfo.builder()
			.followingInfoList(followingInfoList)
			.last(last)
			.build();
	}

	@Override
	public FollowerList findFollowerListByMemberIdAndSettleType(Long memberId,
		String settleType, Long lastFollowId, int pageSize) {

		List<Follow> followList = jpaQueryFactory.selectFrom(follow)
				.where(follow.followerMemberId.eq(memberId), checkSettleType(settleType), ltFollowId(lastFollowId))
				.orderBy(follow.id.desc())
				.limit(pageSize + 1)
				.fetch();

		boolean last = checkLastPage(followList, pageSize);

		return new FollowerList(followList, last);
	}

	@Override
	public Map<Long, Long> findFollowerInvestmentList() {
		return jpaQueryFactory
			.from(follow)
			.where(follow.settled.isFalse())
			.groupBy(follow.followingMemberId)
			.transform(groupBy(follow.followingMemberId).as(follow.investment.sum().coalesce(0L)));
	}

	@Override
	public Map<Long, Long> findFollowingInvestmentList() {
		return jpaQueryFactory
			.from(follow)
			.where(follow.settled.isFalse())
			.groupBy(follow.followerMemberId)
			.transform(groupBy(follow.followerMemberId).as(follow.investment.sum().coalesce(0L)));
	}

	@Override
	public List<FollowerInfoResponse> findFollowerInfosByFollowingId(Long followingId) {
		return jpaQueryFactory
			.select(new QFollowerInfoResponse(follow.followerMemberId, follow.cash))
			.from(follow)
			.where(follow.followingMemberId.eq(followingId))
			.fetch();
	}

	@Override
	public void updateFollower(Long followingId, Long followerId, Long cash) {
		jpaQueryFactory.update(follow).set(follow.cash, cash)
			.where(follow.followingMemberId.eq(followingId),
				follow.followerMemberId.eq(followerId))
			.execute();
	}

	@Override
	public Boolean isFollowedByMemberId(Long loginId, Long memberId) {
		return !jpaQueryFactory
			.from(follow)
			.where(follow.followerMemberId.eq(loginId),
				follow.followingMemberId.eq(memberId),
				follow.settled.isNull().or(follow.settled.isFalse()))
			.fetch().isEmpty();
	}

	@Override
	public Long getFollowingCashByMemberId(Long memberId) {
		return jpaQueryFactory.select(follow.investment.sum().coalesce(0L))
			.from(follow)
			.where(follow.followerMemberId.eq(memberId),
				follow.settled.isNull().or(follow.settled.isFalse()))
			.fetchFirst();
	}

	@Override
	public Long getFollowerCashByMemberId(Long memberId) {
		return jpaQueryFactory.select(follow.investment.sum().coalesce(0L))
			.from(follow)
			.where(follow.followingMemberId.eq(memberId),
				follow.settled.isNull().or(follow.settled.isFalse()))
			.fetchFirst();
	}

	private BooleanExpression ltFollowId(Long followId) {
		if (followId == null) { // 요청이 처음일 때 where 절에 null을 주면 page size만큼 반환
			return null;
		}
		return follow.id.lt(followId);
	}

	private boolean checkLastPage(List<?> results, int pageSize) {
		boolean last = true;
		// 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
		if (results.size() > pageSize) {
			last = false;
			results.remove(pageSize);
		}
		return last;
	}

	private BooleanExpression checkSettleType(String settleType) {
		if (settleType == null) {
			throw new FollowException(SETTLE_TYPE_NOT_FOUND);
		} else if (settleType.equals(SettleType.ALL.getValue())) {
			return null;
		} else if (settleType.equals(SettleType.FOLLOWING.getValue())) {
			return follow.settled.eq(false);
		} else if (settleType.equals(SettleType.SETTLED.getValue())) {
			return follow.settled.eq(true);
		} else {
			throw new FollowException(SETTLE_TYPE_NOT_FOUND);
		}
	}
}
