package com.found_404.funco.portfolio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.member.domain.Member;
import com.found_404.funco.member.domain.repository.MemberRepository;
import com.found_404.funco.member.domain.type.PortfolioStatusType;
import com.found_404.funco.member.exception.MemberErrorCode;
import com.found_404.funco.member.exception.MemberException;
import com.found_404.funco.portfolio.dto.request.PortfolioStatusRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PortfolioService {
	private final MemberRepository memberRepository;

	@Transactional
	public void updatePortfolioStatus(Long memberId, PortfolioStatusRequest portfolioStatusRequest) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));

		member.updatePortfolioStatus(
			PortfolioStatusType.valueOf(portfolioStatusRequest.portfolioStatus().toUpperCase()),
			portfolioStatusRequest.portfolioPrice());
	}
}
