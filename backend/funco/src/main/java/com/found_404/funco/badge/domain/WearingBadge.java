package com.found_404.funco.badge.domain;

import com.found_404.funco.global.entity.BaseEntity;
import com.found_404.funco.member.domain.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
			"member_id",
			"holding_badge_id"
		}
	)
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WearingBadge extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@OneToOne
	@JoinColumn(name = "holding_badge_id", nullable = false)
	private HoldingBadge holdingBadge;

	@Builder
	public WearingBadge(Member member, HoldingBadge holdingBadge) {
		this.member = member;
		this.holdingBadge = holdingBadge;
	}
}
