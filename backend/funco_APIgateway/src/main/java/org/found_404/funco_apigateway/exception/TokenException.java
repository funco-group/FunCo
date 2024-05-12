package org.found_404.funco_apigateway.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {
	private final TokenErrorCode errorCode;
	private final String errorMessage;
	private final HttpStatus httpStatus;

	public TokenException(TokenErrorCode errorCode, HttpStatus httpStatus) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getMessage();
		this.httpStatus = httpStatus;
	}
}
