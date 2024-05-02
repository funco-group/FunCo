package com.found_404.funco.portfolio.domain.repository.impl;

import static com.found_404.funco.follow.domain.QFollow.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.found_404.funco.portfolio.domain.repository.SubscribeCustomRepository;
import com.found_404.funco.portfolio.dto.FollowerInfo;
import com.found_404.funco.portfolio.dto.QFollowerInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SubscribeCustomRepositoryImpl implements SubscribeCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<FollowerInfo> findFollowInfoByFollowingId(Long followingId) {
		return jpaQueryFactory
			.select(new QFollowerInfo(follow.follower.id, follow.cash))
			.from(follow)
			.where(follow.following.id.eq(followingId))
			.fetch();
	}

	@Override
	public void updateFollower(Long followingId, Long followerId, Long cash) {
		jpaQueryFactory.update(follow).set(follow.cash, cash)
			.where(follow.following.id.eq(followingId),
				follow.follower.id.eq(followerId))
			.execute();
	}
}
