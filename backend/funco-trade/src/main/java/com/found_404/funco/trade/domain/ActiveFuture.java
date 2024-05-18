package com.found_404.funco.trade.domain;

import org.hibernate.annotations.Comment;

import com.found_404.funco.global.entity.BaseEntity;
import com.found_404.funco.trade.domain.type.TradeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActiveFuture extends BaseEntity {

	@Comment("member seq")
	@Column(nullable = false)
	private Long memberId;

	@Comment("코인명")
	@Column(length = 20, nullable = false)
	private String ticker;

	@Comment("구분")
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private TradeType tradeType;

	@Comment("주문 금액")
	@Column(nullable = false)
	private Long orderCash;

	@Comment("진입 가격")
	@Column(nullable = false)
	private Double price;

	@Comment("배율")
	@Column(nullable = false)
	private Integer leverage;

	@Builder
	public ActiveFuture(Long memberId, String ticker, TradeType tradeType, Long orderCash, Double price,
		Integer leverage) {
		this.memberId = memberId;
		this.ticker = ticker;
		this.tradeType = tradeType;
		this.orderCash = orderCash;
		this.price = price;
		this.leverage = leverage;
	}
}
