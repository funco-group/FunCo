package com.found_404.funco.follow.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FollowErrorCode {
	FOLLOW_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 팔로우를 찾을 수 없습니다."),
	FOLLOW_DUPLICATED_ERROR(HttpStatus.BAD_REQUEST, "중복된 팔로우입니다."),
	SETTLE_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "팔로워 목록 필터 타입이 없는 타입입니다."),
	FOLLOW_SELF_ERROR(HttpStatus.BAD_REQUEST, "본인을 팔로우 할 수 없습니다."),
	INSUFFICIENT_ASSET(HttpStatus.BAD_REQUEST,"자산이 부족합니다."),
	TRADE_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "trade server error"),
	NOTIFICATION_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "notification server error")

	;

	private final HttpStatus httpStatus;
	private final String errorMsg;
}
