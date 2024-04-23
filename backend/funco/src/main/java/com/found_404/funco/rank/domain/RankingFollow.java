package com.found_404.funco.rank.domain;

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
public class RankingFollow extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "season_id", nullable = false)
	private Season season;

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
	public RankingFollow(Member member, Season season, Long rank, Long totalAsset, Long followInvestment) {
		this.member = member;
		this.season = season;
		this.rank = rank;
		this.totalAsset = totalAsset;
		this.followInvestment = followInvestment;
	}
}
