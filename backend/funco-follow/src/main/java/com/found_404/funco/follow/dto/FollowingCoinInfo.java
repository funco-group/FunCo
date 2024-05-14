package com.found_404.funco.follow.dto;

import com.querydsl.core.annotations.QueryProjection;

public record FollowingCoinInfo(
	MemberInfo memberInfo,
	Long cash,
	Long followingAsset
) {

	@QueryProjection
	public FollowingCoinInfo {}
}
