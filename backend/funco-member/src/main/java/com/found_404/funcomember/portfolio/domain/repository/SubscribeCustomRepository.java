package com.found_404.funcomember.portfolio.domain.repository;

import java.util.List;

import com.found_404.funcomember.portfolio.dto.FollowerInfo;

public interface SubscribeCustomRepository {
	List<FollowerInfo> findFollowInfoByFollowingId(Long followingId);

	void updateFollower(Long followingId, Long followerId, Long cash);
}
