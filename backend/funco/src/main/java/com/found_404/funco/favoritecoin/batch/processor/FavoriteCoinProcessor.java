package com.found_404.funco.favoritecoin.batch.processor;

import java.util.Set;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.redis.core.RedisTemplate;

import com.found_404.funco.favoritecoin.batch.dto.FavoriteCoinBatchInfo;
import com.found_404.funco.favoritecoin.dto.FavoriteCoinInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FavoriteCoinProcessor implements ItemProcessor<String, FavoriteCoinBatchInfo> {
	private RedisTemplate<String, Object> favoriteCoinRedisTemplate;

	public FavoriteCoinProcessor(RedisTemplate<String, Object> favoriteCoinRedisTemplate) {
		this.favoriteCoinRedisTemplate = favoriteCoinRedisTemplate;
	}

	@Override
	public FavoriteCoinBatchInfo process(String memberId) {
		log.info("=================== process() operates ===================");
		// Redis에서 사용자의 관심 코인 정보 가져오기
		Set<String> tickers = getFavoriteCoinInfoFromRedis(memberId);
		if (tickers == null) {
			return null;
		}
		FavoriteCoinBatchInfo favoriteCoinBatchInfo = FavoriteCoinBatchInfo.builder()
			.memberId(Long.valueOf(memberId))
			.tickers(tickers)
			.build();
		return favoriteCoinBatchInfo;
	}

	private Set<String> getFavoriteCoinInfoFromRedis(String memberId) {
		FavoriteCoinInfo favoriteCoinInfo = (FavoriteCoinInfo)favoriteCoinRedisTemplate.opsForValue().get(memberId);
		if (favoriteCoinInfo == null) {
			return null;
		}
		return favoriteCoinInfo.getTickerSet();
	}
}