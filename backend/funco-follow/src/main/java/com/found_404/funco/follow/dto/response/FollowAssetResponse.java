package com.found_404.funco.follow.dto.response;

import lombok.Builder;

@Builder
public record FollowAssetResponse(
	Long followingCash,
	Long followerCash
) {
}
