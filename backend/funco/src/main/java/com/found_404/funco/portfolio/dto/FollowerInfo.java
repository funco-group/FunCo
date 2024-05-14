package com.found_404.funco.portfolio.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record FollowerInfo(
	Long followerId,
	Long cash
) {
	@QueryProjection
	public FollowerInfo(Long followerId, Long cash) {
		this.followerId = followerId;
		this.cash = cash;
	}
}
