package com.found_404.funco.note.domain;

import com.found_404.funco.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note extends BaseEntity {

	private Long memberId;

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
	public Note(Long memberId, String title, String content, String ticker, String thumbnailImage, String thumbnailContent) {
		this.memberId = memberId;
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
