package com.found_404.funcomember.portfolio.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PortfolioErrorCode {
	INSUFFICIENT_CASH(HttpStatus.BAD_REQUEST, "가용 자산이 충분하지 않습니다."),
	NOT_PRIVATE_STATUS(HttpStatus.BAD_REQUEST, "판매 중인 상태가 아닙니다."),
	DUPLICATED_SUBSCRIBE(HttpStatus.BAD_REQUEST, "이미 구독중입니다.")
	;

	private final HttpStatus httpStatus;
	private final String errorMsg;

}
