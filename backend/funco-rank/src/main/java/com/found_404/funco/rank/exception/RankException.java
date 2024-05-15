package com.found_404.funco.rank.exception;

import com.found_404.funco.global.exception.BaseException;

import lombok.Getter;

@Getter
public class RankException extends BaseException {
	public RankException(RankErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getErrorMsg());
	}
}
