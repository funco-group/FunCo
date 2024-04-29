package com.found_404.funco.portfolio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.member.domain.Member;
import com.found_404.funco.member.domain.repository.MemberRepository;
import com.found_404.funco.member.domain.type.PortfolioStatusType;
import com.found_404.funco.member.exception.MemberErrorCode;
import com.found_404.funco.member.exception.MemberException;
import com.found_404.funco.portfolio.dto.request.PortfolioStatusRequest;
import com.found_404.funco.portfolio.service.PortfolioService;

@ExtendWith(MockitoExtension.class)
public class PortfolioServiceTest {
	@Mock
	private MemberRepository memberRepository;
	@InjectMocks
	PortfolioService portfolioService;

	@Test
	@Transactional
	@DisplayName("포트폴리오 공개 여부 수정 성공")
	void updatePortfolioStatusSuccess() {
		// given
		Long memberId = 1L;

		Member member = mock(Member.class);

		PortfolioStatusRequest portfolioStatusRequest = PortfolioStatusRequest.builder()
			.portfolioStatus("private")
			.portfolioPrice(30000L)
			.build();

		given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

		// when
		portfolioService.updatePortfolioStatus(memberId, portfolioStatusRequest);

		// then
		verify(member).updatePortfolioStatus(PortfolioStatusType.PRIVATE);
		verify(member).updatePortfolioPrice(30000L);
	}

	@Test
	@Transactional
	@DisplayName("포트폴리오 공개 여부 수정 실패")
	void updatePortfolioStatusFail() {
		// given
		Long memberId = 1L;

		PortfolioStatusRequest portfolioStatusRequest = PortfolioStatusRequest.builder()
			.portfolioStatus("private")
			.portfolioPrice(30000L)
			.build();

		given(memberRepository.findById(memberId)).willReturn(Optional.empty());

		// when
		MemberException exception = assertThrows(MemberException.class, () ->
			portfolioService.updatePortfolioStatus(memberId, portfolioStatusRequest)
		);

		// then
		assertEquals(exception.getErrorCode(), MemberErrorCode.NOT_FOUND_MEMBER.name());
		assertEquals(exception.getHttpStatus(), MemberErrorCode.NOT_FOUND_MEMBER.getHttpStatus());
		assertEquals(exception.getMessage(), MemberErrorCode.NOT_FOUND_MEMBER.getErrorMsg());
	}
}
