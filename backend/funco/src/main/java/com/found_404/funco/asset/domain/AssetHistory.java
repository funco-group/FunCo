package com.found_404.funco.asset.domain;

import java.time.LocalDateTime;

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
import lombok.Builder;
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
	private Double volume;

	@Comment("거래 단가")
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

	@Comment("주문 금액")
	private Long orderCash;

	@Comment("가상화폐 티커")
	private String ticker;

	@Comment("포트폴리오 유저명")
	private String portfolioName;

	@Comment("팔로우 투자 금액")
	private Long investment;

	@Comment("팔로우 유저명")
	private String followName;

	@Comment("팔로우 시작일")
	private LocalDateTime followDate;

	@Comment("팔로우 수익률")
	private Double followReturnRate;

	@Builder
	public AssetHistory(Member member, AssetType assetType, AssetTradeType assetTradeType, Double volume, Long price,
		Long commission, Long settlement, Long beginningCash, Long endingCash, Long orderCash, String ticker,
		String portfolioName, Long investment, String followName, LocalDateTime followDate, Double followReturnRate) {
		this.member = member;
		this.assetType = assetType;
		this.assetTradeType = assetTradeType;
		this.volume = volume;
		this.price = price;
		this.commission = commission;
		this.settlement = settlement;
		this.beginningCash = beginningCash;
		this.endingCash = endingCash;
		this.orderCash = orderCash;
		this.ticker = ticker;
		this.portfolioName = portfolioName;
		this.investment = investment;
		this.followName = followName;
		this.followDate = followDate;
		this.followReturnRate = followReturnRate;
	}

}
