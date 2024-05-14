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

	@Comment("썸네일 이미지")
	@Column(nullable = false)
	private String thumbnailImage;

	@Comment("썸네일 내용")
	@Column(nullable = false)
	private String thumbnailContent;

	@Builder
	public Note(Member member, String title, String content, String ticker, String thumbnailImage, String thumbnailContent) {
		this.member = member;
		this.title = title;
		this.content = content;
		this.ticker = ticker;
		this.thumbnailImage = thumbnailImage;
		this.thumbnailContent = thumbnailContent;
	}

	public void editNote(String title, String content, String ticker, String thumbnailImage, String thumbnailContent) {
		this.title = title;
		this.content = content;
		this.ticker = ticker;
		this.thumbnailImage = thumbnailImage;
		this.thumbnailContent = thumbnailContent;
	}
}
