package com.found_404.funco.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisErrorCode {
	REDIS_SEVER_OFF(HttpStatus.SERVICE_UNAVAILABLE, "Redis 서버가 꺼졌습니다.");

	private final HttpStatus httpStatus;
	private final String errorMsg;
}
