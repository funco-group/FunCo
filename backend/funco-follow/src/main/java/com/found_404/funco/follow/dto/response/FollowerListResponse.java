package com.found_404.funco.follow.dto.response;

import java.util.List;

public record FollowerListResponse(
	Boolean last,
	List<FollowerResponse> followers) {
}
