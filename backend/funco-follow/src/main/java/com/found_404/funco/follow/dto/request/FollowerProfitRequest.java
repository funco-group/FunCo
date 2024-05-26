package com.found_404.funco.follow.dto.request;

import lombok.Builder;

@Builder
public record FollowerProfitRequest(

	Long followerId,
	Long cash
) {

}
