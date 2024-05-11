package com.found_404.funcomember.auth.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
	String accessToken
) {
}
