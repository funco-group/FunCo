package com.found_404.funcomember.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record GoogleToken(
	String accessToken,
	Integer expiresIn,
	String refreshToken,
	String scope,
	String tokenType,
	String idToken
) {
}