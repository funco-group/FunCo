package com.found_404.funco.follow.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record FollowingListResponse(
	List<FollowingResponse> followings,
	Boolean last
) {

}
