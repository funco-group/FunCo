package com.found_404.funco.asset.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssetErrorCode {
	INIT_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "초기화는 하루(24시간)에 1회로 제한됩니다."),
	MEMBER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "멤버 서버 에러"),
	TRADE_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "거래 서버 에러"),
	FOLLOW_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "팔로우 서버 에러");

	private final HttpStatus httpStatus;
	private final String errorMsg;
}

