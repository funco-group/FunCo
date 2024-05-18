package com.found_404.funco.auth.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
	USED_AUTH_CODE(HttpStatus.BAD_REQUEST, "사용된 인가 코드입니다."),
	MEMBER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "MEMBER 서버 에러.");

	private final HttpStatus httpStatus;
	private final String errorMsg;
}