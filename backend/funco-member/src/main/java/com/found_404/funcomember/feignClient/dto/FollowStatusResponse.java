package com.found_404.funcomember.feignClient.dto;

import lombok.Builder;

@Builder
public record FollowStatusResponse(
	Boolean followed
) {
}
