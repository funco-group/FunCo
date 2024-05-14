package com.found_404.funco.portfolio.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import com.found_404.funco.global.entity.BaseEntity;
import com.found_404.funco.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(uniqueConstraints = {
	@UniqueConstraint(
		columnNames = {
			"from_member_id",
			"to_member_id"
		}
	)
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscribe extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_member_id", nullable = false)
	private Member fromMember;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_member_id", nullable = false)
	private Member toMember;

	@Comment("주문금액")
	@Column(nullable = false)
	private Long orderCash;

	@Comment("구독 만료 일자")
	@Column(nullable = false)
	private LocalDateTime expiredAt;

	@Builder
	public Subscribe(Member fromMember, Member toMember, Long orderCash, LocalDateTime expiredAt) {
		this.fromMember = fromMember;
		this.toMember = toMember;
		this.orderCash = orderCash;
		this.expiredAt = expiredAt;
	}
}
