package com.found_404.funco.favoritecoin.batch.exception;

import com.found_404.funco.global.exception.BaseException;

import lombok.Getter;

@Getter
public class FavoriteCoinBatchException extends BaseException {
	public FavoriteCoinBatchException(FavoriteCoinBatchErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getErrorMsg());
	}
}
