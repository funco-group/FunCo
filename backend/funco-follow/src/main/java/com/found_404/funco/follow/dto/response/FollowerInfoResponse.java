package com.found_404.funco.follow.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record FollowerInfoResponse(
	Long followerId,
	Long cash
) {
	@QueryProjection
	public FollowerInfoResponse(Long followerId, Long cash) {
		this.followerId = followerId;
		this.cash = cash;
	}
}
