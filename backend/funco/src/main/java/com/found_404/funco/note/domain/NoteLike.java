package com.found_404.funco.note.domain;

import com.found_404.funco.global.entity.BaseEntity;
import com.found_404.funco.member.domain.Member;

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
			"member_id",
			"note_id"
		}
	)
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoteLike extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "note_id", nullable = false)
	private Note note;

	@Builder
	public NoteLike(Member member, Note note) {
		this.member = member;
		this.note = note;
	}
}
