package com.found_404.funco.favoritecoin.batch.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FavoriteCoinBatchErrorCode {
	BATCH_FAILED(HttpStatus.BAD_REQUEST, "배치 실행을 실패하였습니다."),
	NOT_FOUND_CONTENT(HttpStatus.NOT_FOUND, "해당 인덱스에 대한 데이터가 존재하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String errorMsg;

}
