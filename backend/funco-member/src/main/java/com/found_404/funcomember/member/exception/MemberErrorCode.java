package com.found_404.funcomember.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),
    TRADE_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TRADE 서버 에러."),
    FOLLOW_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FOLLOW 서버 에러.")
    ;

    private final HttpStatus httpStatus;
    private final String errorMsg;

}
