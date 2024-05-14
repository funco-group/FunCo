package com.found_404.funco.note.domain;

import static java.lang.Boolean.*;

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
public class NoteComment extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

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
	private Boolean deleted = FALSE;

	@Builder
	public NoteComment(Member member, Note note, Long parentId, String content, Boolean deleted) {
		this.member = member;
		this.note = note;
		this.parentId = parentId;
		this.content = content;
		this.deleted = deleted;
	}

	public void editNoteComment(String content) {
		this.content = content;
	}
}
