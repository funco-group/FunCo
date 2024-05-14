package com.found_404.funco.portfolio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;
import java.util.List;
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
import com.found_404.funco.portfolio.domain.Subscribe;
import com.found_404.funco.portfolio.domain.repository.SubscribeRepository;
import com.found_404.funco.portfolio.dto.FollowerInfo;
import com.found_404.funco.portfolio.dto.request.PortfolioStatusRequest;
import com.found_404.funco.portfolio.dto.request.SubscribeRequest;
import com.found_404.funco.portfolio.exception.PortfolioErrorCode;
import com.found_404.funco.portfolio.exception.PortfolioException;
import com.found_404.funco.portfolio.service.PortfolioService;

@ExtendWith(MockitoExtension.class)
public class PortfolioServiceTest {
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private SubscribeRepository subscribeRepository;
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
		verify(member).updatePortfolioStatus(PortfolioStatusType.PRIVATE, 30000L);
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

	@Test
	@Transactional
	@DisplayName("포트폴리오 구매 성공")
	void subscribeSuccess() {
		// given
		Long memberId = 1L;
		Long sellerId = 2L;

		Member subscriber = Member.builder()
			.cash(100000L)
			.build();
		Member seller = Member.builder()
			.cash(750000L)
			.portfolioStatus(PortfolioStatusType.PRIVATE)
			.portfolioPrice(50000L)
			.build();
		SubscribeRequest subscribeRequest = SubscribeRequest.builder().memberId(sellerId).build();

		// when
		given(memberRepository.findById(memberId)).willReturn(Optional.of(subscriber));
		given(memberRepository.findById(sellerId)).willReturn(Optional.of(seller));

		portfolioService.createPortfolio(memberId, subscribeRequest);

		// then
		assertEquals(50000L, subscriber.getCash());
		assertEquals(800000L, seller.getCash());
		verify(subscribeRepository, times(1)).save(any(Subscribe.class));
	}

	@Test
	@Transactional
	@DisplayName("포트폴리오 구매 후 팔로워 동기화 성공")
	void synchronizeFollowersSuccess() {
		// given
		Long followingId = 1L;
		double ratio = 0.5;
		FollowerInfo followerInfo = FollowerInfo.builder()
			.followerId(2L)
			.cash(1000L)
			.build();
		List<FollowerInfo> followerInfos = Collections.singletonList(followerInfo);

		given(subscribeRepository.findFollowInfoByFollowingId(followingId)).willReturn(followerInfos);

		// when
		portfolioService.synchronizeFollowers(followingId, ratio);

		// then
		verify(subscribeRepository, times(1)).updateFollower(eq(followingId), eq(followerInfo.followerId()), anyLong());
	}

	@Test
	@Transactional
	@DisplayName("포트폴리오 구매 실패 - subscriber가 없을 경우")
	void subscribeFail_WhenSubscriberNotFound() {
		// given
		Long memberId = 1L;
		Long sellerId = 2L;

		SubscribeRequest subscribeRequest = SubscribeRequest.builder().memberId(sellerId).build();

		given(memberRepository.findById(memberId)).willReturn(Optional.empty());

		// when
		MemberException exception = assertThrows(MemberException.class, () ->
			portfolioService.createPortfolio(memberId, subscribeRequest)
		);

		// then
		assertEquals(exception.getErrorCode(), MemberErrorCode.NOT_FOUND_MEMBER.name());
		assertEquals(exception.getHttpStatus(), MemberErrorCode.NOT_FOUND_MEMBER.getHttpStatus());
		assertEquals(exception.getMessage(), MemberErrorCode.NOT_FOUND_MEMBER.getErrorMsg());

		verify(memberRepository, never()).save(any(Member.class));
		verify(subscribeRepository, never()).save(any());
	}

	@Test
	@Transactional
	@DisplayName("포트폴리오 구매 실패 - seller 없을 경우")
	void subscribeFail_WhenSellerNotFound() {
		// given
		Long memberId = 1L;
		Long sellerId = 2L;

		Member subscriber = mock(Member.class);
		SubscribeRequest subscribeRequest = SubscribeRequest.builder().memberId(sellerId).build();

		given(memberRepository.findById(memberId)).willReturn(Optional.of(subscriber));
		given(memberRepository.findById(sellerId)).willReturn(Optional.empty());

		// when
		MemberException exception = assertThrows(MemberException.class, () ->
			portfolioService.createPortfolio(memberId, subscribeRequest)
		);

		// then
		assertEquals(exception.getErrorCode(), MemberErrorCode.NOT_FOUND_MEMBER.name());
		assertEquals(exception.getHttpStatus(), MemberErrorCode.NOT_FOUND_MEMBER.getHttpStatus());
		assertEquals(exception.getMessage(), MemberErrorCode.NOT_FOUND_MEMBER.getErrorMsg());

		verify(memberRepository, never()).save(any(Member.class));
		verify(subscribeRepository, never()).save(any());
	}

	@Test
	@Transactional
	@DisplayName("포트폴리오 구매 실패 - subscriber가 자산이 충분하지 않을 경우")
	void subscribeFail_InsufficientCash() {
		// given
		Long memberId = 1L;
		Long sellerId = 2L;

		Member subscriber = Member.builder()
			.cash(50000L)
			.build();
		Member seller = Member.builder()
			.cash(750000L)
			.portfolioStatus(PortfolioStatusType.PRIVATE)
			.portfolioPrice(100000L)
			.build();
		SubscribeRequest subscribeRequest = SubscribeRequest.builder().memberId(sellerId).build();

		given(memberRepository.findById(memberId)).willReturn(Optional.of(subscriber));
		given(memberRepository.findById(sellerId)).willReturn(Optional.of(seller));

		// when
		PortfolioException exception = assertThrows(PortfolioException.class, () ->
			portfolioService.createPortfolio(memberId, subscribeRequest)
		);

		// then
		assertTrue(exception instanceof PortfolioException);
		assertEquals(exception.getErrorCode(), PortfolioErrorCode.INSUFFICIENT_CASH.name());
		assertEquals(exception.getHttpStatus(), PortfolioErrorCode.INSUFFICIENT_CASH.getHttpStatus());
		assertEquals(exception.getMessage(), PortfolioErrorCode.INSUFFICIENT_CASH.getErrorMsg());

		verify(memberRepository, never()).save(any(Member.class));
		verify(subscribeRepository, never()).save(any());
	}
}
