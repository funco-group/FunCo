package com.found_404.funco.asset.domain;

import org.hibernate.annotations.Comment;

import com.found_404.funco.asset.domain.type.AssetTradeType;
import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.global.entity.BaseEntity;
import com.found_404.funco.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssetHistory extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Comment("자산 구분")
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private AssetType assetType;

	@Comment("거래 구분")
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private AssetTradeType assetTradeType;

	@Comment("거래 수량")
	@Column(nullable = false)
	private Double volume;

	@Comment("거래 단가")
	@Column(nullable = false)
	private Long price;

	@Comment("수수료")
	private Long commission;

	@Comment("정산 금액")
	private Long settlement;

	@Comment("거래 전 가용 현금")
	@Column(nullable = false)
	private Long beginningCash;

	@Comment("거래 후 가용 현금")
	@Column(nullable = false)
	private Long endingCash;
}
