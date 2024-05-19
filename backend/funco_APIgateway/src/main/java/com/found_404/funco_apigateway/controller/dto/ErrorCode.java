package com.found_404.funco_apigateway.controller.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {

    FALLBACK("9999", "서버에 연결에 실패하였습니다. 잠시 후 다시 시도해주세요.");

    private final String code;
    private final String message;

    ErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

}