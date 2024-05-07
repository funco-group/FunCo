package com.found_404.funco.portfolio.exception;

import com.found_404.funco.global.exception.BaseException;

import lombok.Getter;

@Getter
public class PortfolioException extends BaseException {
	public PortfolioException(PortfolioErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getErrorMsg());
	}
}
