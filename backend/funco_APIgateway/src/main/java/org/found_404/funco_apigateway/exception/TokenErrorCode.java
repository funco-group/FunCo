package org.found_404.funco_apigateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenErrorCode {
	EXPIRED_TOKEN("만료된 토큰입니다."),
	EXPIRED_REFRESH_TOKEN("만료된 refresh 토큰입니다."),
	INVALID_TOKEN("유효하지 않은 토큰입니다."),
	EMPTY_TOKEN("토큰이 존재하지 않습니다."),
	SERVER_ERROR("서버 에러");
	private final String message;
}
