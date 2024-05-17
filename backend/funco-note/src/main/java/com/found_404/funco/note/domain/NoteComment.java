package com.found_404.funco.note.domain;

import com.found_404.funco.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import static java.lang.Boolean.FALSE;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoteComment extends BaseEntity {

	private Long memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "note_id", nullable = false)
	private Note note;

	@Comment("부모 댓글 ID")
	private Long parentId;

	@Comment("내용")
	@Column(nullable = false)
	private String content;

	@Comment("삭제 여부")
	@Column(nullable = false)
	private Boolean deleted;

	@Builder
	public NoteComment(Long memberId, Note note, Long parentId, String content) {
		this.memberId = memberId;
		this.note = note;
		this.parentId = parentId;
		this.content = content;
		this.deleted = FALSE;
	}

	public void editNoteComment(String content) {
		this.content = content;
	}

	public void softDelete() {
		this.content = "";
		this.deleted = Boolean.TRUE;
	}
}
