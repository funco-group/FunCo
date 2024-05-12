package com.found_404.funco.member.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import com.found_404.funco.auth.dto.OauthId;
import com.found_404.funco.global.entity.BaseEntity;
import com.found_404.funco.global.util.DecimalCalculator;
import com.found_404.funco.global.util.ScaleType;
import com.found_404.funco.member.domain.type.MemberStatus;
import com.found_404.funco.member.domain.type.PortfolioStatusType;

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
public class Member extends BaseEntity {

	@Comment("OAuth 코드")
	@Column(nullable = false)
	private OauthId oauthId;

	@Comment("닉네임")
	@Column(length = 20)
	private String nickname;

	@Comment("프로필 이미지 URL")
	@Column(length = 2100)
	private String profileUrl;

	@Comment("한 줄 소개")
	@Column(length = 100)
	private String introduction;

	@Comment("가용 현금")
	@Column(nullable = false)
	private Long cash;

	@Comment("회원 유형")
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private MemberStatus status;

	@Comment("포트폴리오 상태")
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private PortfolioStatusType portfolioStatus;

	@Comment("포트폴리오 가격")
	private Long portfolioPrice;

	@Comment("뱃지 착용 여부")
	@Column(nullable = false)
	private Boolean badgeYn;

	@Comment("마지막 원 초기화 날짜")
	private LocalDateTime initCashDate;

	private static final Double COMMISSION = 0.05;

	private static final long RE_INIT_CASH = 5_000_000;

	@Builder
	public Member(OauthId oauthId, String nickname, String profileUrl, String introduction, Long cash,
		MemberStatus status,
		PortfolioStatusType portfolioStatus, Long portfolioPrice, Boolean badgeYn, LocalDateTime initCashDate) {
		this.oauthId = oauthId;
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.introduction = introduction;
		this.cash = cash;
		this.status = status;
		this.portfolioStatus = portfolioStatus;
		this.portfolioPrice = portfolioPrice;
		this.badgeYn = badgeYn;
		this.initCashDate = initCashDate;
	}

	public void decreaseCash(long orderCash) {
		if (this.cash < orderCash) {
			throw new RuntimeException("잔액이 부족합니다.");  // member domain에서 custom exception 추가
		}
		this.cash -= orderCash;
	}

	public void increaseCash(long orderCash) {
		this.cash += getCashWithCommission(orderCash);
	}

	public void increaseCashWithoutCommission(long orderCash) {
		this.cash += orderCash;
	}

	public long getCashWithCommission(long orderCash) {
		return orderCash - (long)(DecimalCalculator.multiple(orderCash, COMMISSION, ScaleType.NORMAL_SCALE));
	}

	public void settleCash(long settlement) {
		this.cash += settlement;
	}

	public void recoverCash(long cash) {
		this.cash += cash;
	}

	public void updateOauthId(OauthId oauthId) {
		this.oauthId = oauthId;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void updatePortfolioStatus(PortfolioStatusType portfolioStatus, Long portfolioPrice) {
		this.portfolioStatus = portfolioStatus;
		this.portfolioPrice = portfolioPrice;
	}

	public void updateInitCashDate(LocalDateTime initCashDate) {
		this.initCashDate = initCashDate;
	}

	public void updateInitCash() {
		this.cash = RE_INIT_CASH;
	}

	public void withdraw() {
		this.status = MemberStatus.WITHDRAW;
	}
}
