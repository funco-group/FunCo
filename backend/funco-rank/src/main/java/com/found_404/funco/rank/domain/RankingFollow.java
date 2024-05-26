package com.found_404.funco.rank.domain;

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
public class RankingFollow extends BaseEntity {

	@Comment("멤버 아이디")
	@Column(nullable = false)
	private Long memberId;

	@Comment("순위")
	@Column(nullable = false)
	private Long rank;

	@Comment("총 자산")
	@Column(nullable = false)
	private Long totalAsset;

	@Comment("총 팔로우 투자")
	@Column(nullable = false)
	private Long followInvestment;

	@Builder
	public RankingFollow(Long memberId, Long rank, Long totalAsset, Long followInvestment) {
		this.memberId = memberId;
		this.rank = rank;
		this.totalAsset = totalAsset;
		this.followInvestment = followInvestment;
	}
}
