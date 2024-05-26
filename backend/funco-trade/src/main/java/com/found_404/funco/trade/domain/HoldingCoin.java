package com.found_404.funco.trade.domain;

import static com.found_404.funco.global.util.DecimalCalculator.*;
import static com.found_404.funco.global.util.DecimalCalculator.ScaleType.*;
import static com.found_404.funco.trade.exception.TradeErrorCode.*;

import org.hibernate.annotations.Comment;

import com.found_404.funco.global.entity.BaseEntity;
import com.found_404.funco.global.util.CommissionUtil;
import com.found_404.funco.trade.exception.TradeException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(uniqueConstraints = {
	@UniqueConstraint(
		columnNames = {
			"member_id", "ticker"
		}
	)
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HoldingCoin extends BaseEntity {

	@Comment("member seq")
	@Column(nullable = false)
	private Long memberId;

	@Comment("코인명")
	@Column(length = 20, nullable = false)
	private String ticker;

	@Comment("개수")
	@Column(nullable = false)
	private Double volume;

	@Comment("평균단가")
	@Column(nullable = false)
	private Double averagePrice;

	@Builder
	public HoldingCoin(Long memberId, String ticker, Double volume, Double averagePrice) {
		this.memberId = memberId;
		this.ticker = ticker;
		this.volume = volume;
		this.averagePrice = averagePrice;
	}

	public void increaseVolume(double volume, Double price) {
		recoverVolume(CommissionUtil.getVolumeWithoutCommission(volume), price);
	}

	public void decreaseVolume(double volume) {
		if (this.volume < volume) {
			throw new TradeException(INSUFFICIENT_ASSET);
		}
		this.volume = minus(this.volume, volume, VOLUME_SCALE);
	}

	public void recoverVolume(double volume, Double price) {
		this.averagePrice = divide((multiple(this.volume, this.averagePrice, NORMAL_SCALE) + multiple(volume, price, NORMAL_SCALE)), plus(volume, this.volume, NORMAL_SCALE), PRICE_SCALE);
		this.volume = plus(this.volume, volume, VOLUME_SCALE);
	}

}
