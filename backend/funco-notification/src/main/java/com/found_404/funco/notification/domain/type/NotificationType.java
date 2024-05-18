package com.found_404.funco.notification.domain.type;

public enum NotificationType {
	// 매수 알림
	BUY,
	// 매도 알림
	SELL,
	// 정산 알림

	FUTURES,
	// 선물 청산 알림

	SETTLE,
	// 강제정산 알림

	FORCE_SETTLE,
	// 팔로우 되었다 라는 알림

	FOLLOW,
	// 포트폴리오가 팔렸다는 알림

	SELL_PORTFOLIO,

	// 댓글 알림
	NOTE_COMMENT,

	// 대댓글 알림
	COMMENT_ANSWER
}
