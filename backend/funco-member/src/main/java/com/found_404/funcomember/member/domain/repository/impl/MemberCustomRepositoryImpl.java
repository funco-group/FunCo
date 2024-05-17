package com.found_404.funcomember.member.domain.repository.impl;

import static com.found_404.funcomember.member.domain.QMember.*;
import static com.found_404.funcomember.member.domain.type.PortfolioVisibility.*;
import static com.found_404.funcomember.portfolio.domain.QSubscribe.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.found_404.funcomember.member.domain.repository.MemberCustomRepository;
import com.found_404.funcomember.member.domain.type.PortfolioStatusType;
import com.found_404.funcomember.member.dto.MemberInfo;
import com.found_404.funcomember.member.dto.QMemberInfo;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public MemberInfo findMyInfoByMemberId(Long memberId) {
		return jpaQueryFactory
			.select(new QMemberInfo(member.nickname, member.profileUrl, member.introduction, member.cash,
				member.portfolioStatus.stringValue(),
				member.portfolioPrice))
			.from(member)
			.where(member.id.eq(memberId))
			.fetchFirst();
	}

	public MemberInfo findUserInfoByMemberId(Long memberId) {
		CaseBuilder caseBuilder = new CaseBuilder();
		return jpaQueryFactory
			.select(new QMemberInfo(member.id, member.nickname, member.profileUrl, member.introduction, member.cash,
				caseBuilder.when(member.portfolioStatus.stringValue().eq(PortfolioStatusType.PUBLIC.toString()))
					.then(PUBLIC.getValue())
					.when(member.portfolioStatus.stringValue()
						.eq(PortfolioStatusType.PRIVATE.toString())
						.and(subscribe.id.isNotNull())
						.and(subscribe.expiredAt.after(
							LocalDateTime.now())))
					.then(SUBSCRIBE.getValue())
					.otherwise(PRIVATE.getValue())
					.as("portfolioStatus"), member.portfolioPrice))
			.from(subscribe)
			.rightJoin(member).on(subscribe.toMember.id.eq(member.id))
			.where(member.id.eq(memberId))
			.fetchFirst();
	}
}
