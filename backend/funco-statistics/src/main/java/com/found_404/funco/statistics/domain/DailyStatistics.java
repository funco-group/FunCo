package com.found_404.funco.statistics.domain;

import java.time.LocalDate;

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
public class DailyStatistics extends BaseEntity {

	@Comment("멤버 아이디")
	@Column(nullable = false)
	private Long memberId;

	@Comment("날짜")
	@Column(nullable = false)
	private LocalDate date;

	@Comment("손익")
	@Column(nullable = false)
	private Long returnResult;

	@Comment("수익률")
	@Column(nullable = false)
	private Double returnRate;

	@Comment("누적손익")
	@Column(nullable = false)
	private Long accReturnResult;

	@Comment("누적수익률")
	@Column(nullable = false)
	private Double accReturnRate;

	@Comment("기초자산")
	@Column(nullable = false)
	private Long beginningAsset;

	@Comment("기말자산")
	@Column(nullable = false)
	private Long endingAsset;

	@Builder
	public DailyStatistics(Long memberId, LocalDate date, Long returnResult, Double returnRate, Long accReturnResult,
		Double accReturnRate, Long beginningAsset, Long endingAsset) {
		this.memberId = memberId;
		this.date = date;
		this.returnResult = returnResult;
		this.returnRate = returnRate;
		this.accReturnResult = accReturnResult;
		this.accReturnRate = accReturnRate;
		this.beginningAsset = beginningAsset;
		this.endingAsset = endingAsset;
	}
}
