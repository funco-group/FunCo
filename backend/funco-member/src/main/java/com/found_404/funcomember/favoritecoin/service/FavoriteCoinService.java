package com.found_404.funcomember.favoritecoin.service;

import static com.found_404.funcomember.global.type.RedisZSetType.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funcomember.favoritecoin.dto.FavoriteCoinInfo;
import com.found_404.funcomember.favoritecoin.dto.request.FavoriteCoinRequest;
import com.found_404.funcomember.favoritecoin.dto.response.FavoriteCoinResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteCoinService {
	private final RedisTemplate<String, Object> favoriteCoinRedisTemplate;
	private final RedisTemplate<String, Object> favoriteCoinZSetRedisTemplate;

	public void createFavoriteCoin(Long memberId, FavoriteCoinRequest favoriteCoinRequest) {
		FavoriteCoinInfo favoriteCoinInfo = readFavoriteCoinInfo(memberId);
		favoriteCoinInfo.createFavorite(favoriteCoinRequest.ticker());
		updateFavoriteInfo(memberId, favoriteCoinInfo); // redis에 바뀐 정보 업데이트
		updateFavoriteCoinZSet(memberId, favoriteCoinInfo.getUpdatedAt());
	}

	public FavoriteCoinResponse readFavoriteCoin(Long memberId) {
		FavoriteCoinInfo favoriteCoinInfo = readFavoriteCoinInfo(memberId);
		Set<String> tickerSet = favoriteCoinInfo.getTickerSet();
		List<String> tickers = new ArrayList<>(tickerSet != null ? tickerSet : new ArrayList<>());
		return FavoriteCoinResponse.builder()
			.tickers(tickers)
			.build();
	}

	public void deleteFavoriteCoin(Long memberId, String ticker) {
		FavoriteCoinInfo favoriteCoinInfo = readFavoriteCoinInfo(memberId);
		if (favoriteCoinInfo == null) {
			return;
		}
		favoriteCoinInfo.deleteFavorite(ticker);
		updateFavoriteInfo(memberId, favoriteCoinInfo); // redis에 바뀐 정보 업데이트
		updateFavoriteCoinZSet(memberId, LocalDateTime.now());
	}

	// ZSet에 관심코인 업데이트 정보를 추가하는 메서드
	public void updateFavoriteCoinZSet(Long memberId, LocalDateTime updatedAt) {
		double score = updatedAt.toEpochSecond(ZoneOffset.UTC);
		favoriteCoinZSetRedisTemplate.opsForZSet().add(FAVORITE_COIN_ZSET.toString(), memberId.toString(), score);
	}

	private FavoriteCoinInfo readFavoriteCoinInfo(Long memberId) {
		FavoriteCoinInfo favoriteCoinInfo = (FavoriteCoinInfo)favoriteCoinRedisTemplate.opsForValue()
			.get(memberId.toString());
		if (favoriteCoinInfo == null) {
			favoriteCoinInfo = FavoriteCoinInfo.builder().build();
		}
		return favoriteCoinInfo;
	}

	private void updateFavoriteInfo(Long memberId, FavoriteCoinInfo favoriteCoinInfo) {
		favoriteCoinRedisTemplate.opsForValue().set(memberId.toString(), favoriteCoinInfo);
	}
}
