package com.found_404.funco.favoritecoin;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.favoritecoin.dto.FavoriteCoinInfo;
import com.found_404.funco.favoritecoin.dto.request.FavoriteCoinRequest;
import com.found_404.funco.favoritecoin.dto.response.FavoriteCoinResponse;
import com.found_404.funco.favoritecoin.service.FavoriteCoinService;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {
	@Mock
	private RedisTemplate<String, Object> favoriteCoinRedisTemplate;
	@Mock
	private ValueOperations<String, Object> valueOperations;
	@Spy
	@InjectMocks
	private FavoriteCoinService favoriteCoinService;

	@BeforeEach
	void setUp() {
		given(favoriteCoinRedisTemplate.opsForValue()).willReturn(valueOperations);
	}

	@Test
	@Transactional(readOnly = true)
	@DisplayName("관심코인 생성 성공")
	void createFavoriteCoinSuccess() {
		// given
		Long memberId = 1L;
		FavoriteCoinRequest request = new FavoriteCoinRequest("KRW-BTC");
		FavoriteCoinInfo info = FavoriteCoinInfo.builder().build();

		given(valueOperations.get(memberId.toString())).willReturn(info);
		doNothing().when(favoriteCoinService).updateFavoriteCoinZSet(eq(memberId), any(LocalDateTime.class));

		// when
		favoriteCoinService.createFavoriteCoin(memberId, request);

		// then
		verify(valueOperations, times(1)).set(eq(memberId.toString()), any(FavoriteCoinInfo.class));
	}

	@Test
	@Transactional(readOnly = true)
	@DisplayName("관심코인 조회 성공")
	void readFavoriteCoinSuccess() {
		// given
		Long memberId = 1L;
		FavoriteCoinInfo info = FavoriteCoinInfo.builder().build();
		info.createFavorite("KRW-BTC");
		info.createFavorite("KRW-ETH");

		given(favoriteCoinRedisTemplate.opsForValue()).willReturn(valueOperations);
		given(valueOperations.get(memberId.toString())).willReturn(info);

		// when
		FavoriteCoinResponse response = favoriteCoinService.readFavoriteCoin(memberId);

		// then
		assertEquals(new HashSet<>(Arrays.asList("KRW-BTC", "KRW-ETH")), new HashSet<>(response.tickers()));
	}

	@Test
	@DisplayName("관심코인 삭제 성공")
	void deleteFavoriteCoinSuccess() {
		// given
		Long memberId = 1L;
		String tickerToDelete = "KRW-BTC";
		FavoriteCoinInfo info = FavoriteCoinInfo.builder().build();
		info.createFavorite(tickerToDelete);

		given(valueOperations.get(memberId.toString())).willReturn(info);
		doNothing().when(favoriteCoinService).updateFavoriteCoinZSet(eq(memberId), any(LocalDateTime.class));

		// when
		favoriteCoinService.deleteFavoriteCoin(memberId, tickerToDelete);

		// then
		verify(valueOperations, times(1)).set(eq(memberId.toString()), any(FavoriteCoinInfo.class));
	}

}
