package com.found_404.funcomember.global.exception;

import lombok.Getter;

@Getter
public class RedisException extends BaseException {
	public RedisException(RedisErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getErrorMsg());
	}
}
