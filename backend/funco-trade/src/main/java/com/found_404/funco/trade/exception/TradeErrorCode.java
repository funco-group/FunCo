package com.found_404.funco.trade.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradeErrorCode {
    INSUFFICIENT_ASSET(HttpStatus.BAD_REQUEST, "자산이 충분하지 않습니다."),
    PRICE_CONNECTION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "시세 조회에 실패했습니다."),
    NOT_FOUND_TRADE(HttpStatus.NOT_FOUND, "해당 거래를 찾을 수 없습니다."),
    TRADE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "해당 권한이 없습니다."),
    ALREADY_FUTURES_TRADE(HttpStatus.BAD_REQUEST, " 이미 해당 선물 거래를 진행중입니다."),

    FOLLOW_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "팔로우 서버 에러"),
    MEMBER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "멤버 서버 에러"),
    NOTIFICATION_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알림 서버 에러")

    ;

    private final HttpStatus httpStatus;
    private final String errorMsg;

}
