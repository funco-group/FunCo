package com.found_404.funco.note.domain;

import com.found_404.funco.global.entity.BaseEntity;
import jakarta.persistence.*;
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

	private Long memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "note_id", nullable = false)
	private Note note;

	@Builder
	public NoteLike(Long memberId, Note note) {
		this.memberId = memberId;
		this.note = note;
	}
}
