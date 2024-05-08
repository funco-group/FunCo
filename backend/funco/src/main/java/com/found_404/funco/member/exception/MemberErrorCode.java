package com.found_404.funco.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    INVALID_MEMBER(HttpStatus.BAD_REQUEST, "유효하지 않은 회원입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String errorMsg;

}