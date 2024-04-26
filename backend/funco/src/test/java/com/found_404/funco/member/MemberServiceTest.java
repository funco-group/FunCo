package com.found_404.funco.member;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.member.domain.repository.MemberRepository;
import com.found_404.funco.member.dto.MemberInfo;
import com.found_404.funco.member.dto.response.MyInfoResponse;
import com.found_404.funco.member.dto.response.UserInfoResponse;
import com.found_404.funco.member.service.MemberService;
import com.found_404.funco.trade.dto.HoldingCoinsDto;
import com.found_404.funco.trade.dto.RecentTradedCoin;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private RedisTemplate<String, Object> rankZSetRedisTemplate;
	@Mock
	private ZSetOperations<String, Object> zSetOperations;
	@InjectMocks
	private MemberService memberService;

	@BeforeEach
	void setUp() {
		given(rankZSetRedisTemplate.opsForZSet()).willReturn(zSetOperations);
	}

	@Test
	@Transactional(readOnly = true)
	@DisplayName("유저 정보 조회 성공")
	void readUserInfoSuccess() {
		//given
		Long loginMemberId = 1L;
		Long memberId = 2L;
		MemberInfo memberInfo = MemberInfo.builder()
			.nickname("sunju")
			.profileUrl("https")
			.introduction("저만 따라오세요")
			.cash(100000L)
			.portfolioStatus("public")
			.portfolioPrice(500000L)
			.build();

		List<HoldingCoinsDto> holdingCoins = Arrays.asList(
			new HoldingCoinsDto("KRW-BTC", 0.00002),
			new HoldingCoinsDto("KRW-ETH", 0.001)
		);

		List<RecentTradedCoin> recentTradedCoins = Arrays.asList(
			new RecentTradedCoin("KRW-BTC", LocalDateTime.now()),
			new RecentTradedCoin("KRW-ETH", LocalDateTime.now()),
			new RecentTradedCoin("KRW-SOL", LocalDateTime.now())
		);

		given(memberRepository.findHoldingCoinsByMemberId(memberId)).willReturn(holdingCoins);
		given(memberRepository.findUserInfoByMemberId(memberId)).willReturn(memberInfo);
		given(memberRepository.findRecentTradedCoinByMemberId(memberId)).willReturn(recentTradedCoins);
		given(memberRepository.getFollowerCashByMemberId(memberId)).willReturn(50000L);
		given(memberRepository.getFollowingCashByMemberId(memberId)).willReturn(60000L);
		given(memberRepository.isFollowedByMemberId(loginMemberId, memberId)).willReturn(true);
		given(memberRepository.findWearingBadgeByMemberId(memberId)).willReturn(1L);

		// when
		UserInfoResponse userInfoResponse = memberService.readMember(loginMemberId, memberId);

		// then
		assertNotNull(userInfoResponse);
		assertEquals(memberId, userInfoResponse.memberId());
		assertEquals("sunju", userInfoResponse.nickname());
		assertEquals("https", userInfoResponse.profileUrl());
		assertEquals("저만 따라오세요", userInfoResponse.introduction());
		assertEquals(3, userInfoResponse.topCoins().size());
		assertEquals(50000L, userInfoResponse.followerCash());
		assertEquals(60000L, userInfoResponse.followingCash());
		assertTrue(userInfoResponse.isFollow());
		assertEquals("public", userInfoResponse.portfolioStatus());
		assertEquals(500000L, userInfoResponse.portfolioPrice());
		assertEquals(1L, userInfoResponse.badgeId());
	}

	@Test
	@Transactional(readOnly = true)
	@DisplayName("내 정보 조회 성공")
	void readMyInfoSuccess() {
		//given
		Long memberId = 1L;
		MemberInfo memberInfo = MemberInfo.builder()
			.nickname("sunju")
			.profileUrl("https")
			.introduction("저만 따라오세요")
			.cash(100000L)
			.portfolioStatus("public")
			.portfolioPrice(500000L)
			.build();

		List<HoldingCoinsDto> holdingCoins = Arrays.asList(
			new HoldingCoinsDto("KRW-BTC", 0.00002),
			new HoldingCoinsDto("KRW-ETH", 0.001)
		);

		List<RecentTradedCoin> recentTradedCoins = Arrays.asList(
			new RecentTradedCoin("KRW-BTC", LocalDateTime.now()),
			new RecentTradedCoin("KRW-ETH", LocalDateTime.now())
		);

		given(memberRepository.findHoldingCoinsByMemberId(memberId)).willReturn(holdingCoins);
		given(memberRepository.findMyInfoByMemberId(memberId)).willReturn(memberInfo);
		given(memberRepository.findRecentTradedCoinByMemberId(memberId)).willReturn(recentTradedCoins);
		given(memberRepository.getFollowerCashByMemberId(memberId)).willReturn(50000L);
		given(memberRepository.getFollowingCashByMemberId(memberId)).willReturn(60000L);
		given(memberRepository.findWearingBadgeByMemberId(memberId)).willReturn(1L);

		// when
		MyInfoResponse myInfoResponse = memberService.readMember(memberId);

		// then
		assertNotNull(myInfoResponse);
		assertEquals(memberId, myInfoResponse.memberId());
		assertEquals("sunju", myInfoResponse.nickname());
		assertEquals("https", myInfoResponse.profileUrl());
		assertEquals("저만 따라오세요", myInfoResponse.introduction());
		assertEquals(2, myInfoResponse.topCoins().size());
		assertEquals(50000L, myInfoResponse.followerCash());
		assertEquals(60000L, myInfoResponse.followingCash());
		assertEquals(1L, myInfoResponse.badgeId());
	}
}
