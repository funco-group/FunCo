package com.found_404.funco.notification.domain;

import com.found_404.funco.global.entity.BaseEntity;
import com.found_404.funco.notification.domain.type.NotificationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

	private Long memberId;

	@Comment("알림타입")
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private NotificationType type;

	@Comment("알림메시지")
	@Column(nullable = false)
	private String message;

	@Comment("읽음여부")
	@Column(nullable = false)
	private Boolean readYn;

	@Builder
	public Notification(Long memberId, NotificationType type, String message, Boolean readYn) {
		this.memberId = memberId;
		this.type = type;
		this.message = message;
		this.readYn = readYn;
	}

	public void read() {
		this.readYn = true;
	}
}
