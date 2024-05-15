package com.found_404.funco.feignClient.dto;

public enum NotificationType {
    BUY,
    // 매도 알림
    SELL,
    // 정산 알림
    SETTLE,
    // 강제정산 알림
    FORCE_SETTLE,
    // 팔로우 되었다 라는 알림
    FOLLOW,
    // 포트폴리오가 팔렸다는 알림
    SELL_PORTFOLIO
}