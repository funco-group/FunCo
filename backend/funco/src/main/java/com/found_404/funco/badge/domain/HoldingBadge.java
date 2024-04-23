package com.found_404.funco.badge.domain;

import org.hibernate.annotations.Comment;

import com.found_404.funco.global.entity.BaseEntity;
import com.found_404.funco.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HoldingBadge extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "badge_id", nullable = false)
	private Badge badge;

	@Comment("착용 여부")
	@Column(nullable = false)
	private Boolean wearYn;

	@Builder
	public HoldingBadge(Member member, Badge badge, Boolean wearYn) {
		this.member = member;
		this.badge = badge;
		this.wearYn = wearYn;
	}
}
