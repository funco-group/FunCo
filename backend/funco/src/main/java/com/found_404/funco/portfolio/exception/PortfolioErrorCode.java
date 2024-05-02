package com.found_404.funco.portfolio.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PortfolioErrorCode {
	INSUFFICIENT_CASH(HttpStatus.BAD_REQUEST, "가용 자산이 충분하지 않습니다."),
	;

	private final HttpStatus httpStatus;
	private final String errorMsg;

}
