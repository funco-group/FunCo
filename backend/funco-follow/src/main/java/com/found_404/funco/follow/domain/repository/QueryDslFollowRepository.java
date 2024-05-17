package com.found_404.funco.follow.domain.repository;

import java.util.List;
import java.util.Map;

import com.found_404.funco.follow.dto.FollowerList;
import com.found_404.funco.follow.dto.SliceFollowingInfo;
import com.found_404.funco.follow.dto.response.FollowerInfoResponse;
import com.found_404.funco.follow.dto.response.FollowerListResponse;

public interface QueryDslFollowRepository {

	SliceFollowingInfo findFollowingInfoListByMemberId(Long memberId, Long lastFollowId, int pageSize);

	FollowerList findFollowerListByMemberIdAndSettleType(Long memberId, String settleType,
														 Long lastFollowId,
														 int pageSize);

	Map<Long, Long> findFollowerInvestmentList();

	Map<Long, Long> findFollowingInvestmentList();

	List<FollowerInfoResponse> findFollowerInfosByFollowingId(Long followingId);

	void updateFollower(Long followingId, Long followerId, Long cash);

	Boolean isFollowedByMemberId(Long loginId, Long memberId);

	Long getFollowingCashByMemberId(Long memberId);

	Long getFollowerCashByMemberId(Long memberId);
}
