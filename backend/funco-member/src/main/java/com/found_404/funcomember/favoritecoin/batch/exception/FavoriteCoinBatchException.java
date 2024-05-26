package com.found_404.funcomember.favoritecoin.batch.exception;

import com.found_404.funcomember.global.exception.BaseException;

import lombok.Getter;

@Getter
public class FavoriteCoinBatchException extends BaseException {
	public FavoriteCoinBatchException(FavoriteCoinBatchErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getErrorMsg());
	}
}
