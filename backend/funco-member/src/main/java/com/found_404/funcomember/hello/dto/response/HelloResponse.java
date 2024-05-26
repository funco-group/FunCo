package com.found_404.funcomember.hello.dto.response;

import lombok.Builder;

@Builder
public record HelloResponse(
	String message
) {
}
