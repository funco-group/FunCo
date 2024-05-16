package com.found_404.funco.asset.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssetErrorCode {
	INIT_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "초기화는 하루(24시간)에 1회로 제한됩니다.");

	private final HttpStatus httpStatus;
	private final String errorMsg;
}

