package com.found_404.funcomember.member.domain;

import com.found_404.funcomember.member.exception.MemberErrorCode;
import com.found_404.funcomember.member.exception.MemberException;
import org.hibernate.annotations.Comment;

import com.found_404.funcomember.auth.dto.OauthId;
import com.found_404.funcomember.global.entity.BaseEntity;
import com.found_404.funcomember.member.domain.type.MemberStatus;
import com.found_404.funcomember.member.domain.type.PortfolioStatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.found_404.funcomember.member.exception.MemberErrorCode.INSUFFICIENT_BALANCE;

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

	private static final Double COMMISSION = 0.05;

	@Builder
	public Member(OauthId oauthId, String nickname, String profileUrl, String introduction, Long cash,
		MemberStatus status,
		PortfolioStatusType portfolioStatus, Long portfolioPrice) {
		this.oauthId = oauthId;
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.introduction = introduction;
		this.cash = cash;
		this.status = status;
		this.portfolioStatus = portfolioStatus;
		this.portfolioPrice = portfolioPrice;
	}

	public void updateCash(long updateCash) {
		if (this.cash + updateCash < 0) {
			throw new MemberException(INSUFFICIENT_BALANCE);
		}
		this.cash += updateCash;
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

	public void withdraw() {
		this.status = MemberStatus.WITHDRAW;
	}
}
