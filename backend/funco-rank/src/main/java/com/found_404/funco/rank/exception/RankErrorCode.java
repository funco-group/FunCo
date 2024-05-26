package com.found_404.funco.rank.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RankErrorCode {
	RANK_NOT_FOUND(HttpStatus.NOT_FOUND, "랭킹 조회에 실패하였습니다.");

	private final HttpStatus httpStatus;
	private final String errorMsg;

}
