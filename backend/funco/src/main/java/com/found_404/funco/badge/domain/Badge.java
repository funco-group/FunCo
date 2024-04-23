package com.found_404.funco.badge.domain;

import org.hibernate.annotations.Comment;

import com.found_404.funco.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge extends BaseEntity {

	@Comment("뱃지명")
	@Column(nullable = false)
	private String name;

	@Builder
	public Badge(String name) {
		this.name = name;
	}
}
