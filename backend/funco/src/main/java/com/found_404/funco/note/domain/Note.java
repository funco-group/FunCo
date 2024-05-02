package com.found_404.funco.note.domain;

import org.hibernate.annotations.Comment;

import com.found_404.funco.global.entity.BaseEntity;
import com.found_404.funco.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Comment("제목")
	@Column(nullable = false)
	private String title;

	@Comment("내용")
	@Lob
	@Column(nullable = false)
	private String content;

	@Comment("코인명")
	@Column(nullable = false)
	private String ticker;

	@Builder
	public Note(Member member, String title, String content, String ticker) {
		this.member = member;
		this.title = title;
		this.content = content;
		this.ticker = ticker;
	}

	public void editNote(String title, String content, String ticker) {
		this.title = title;
		this.content = content;
		this.ticker = ticker;
	}
}
