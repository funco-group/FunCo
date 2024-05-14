package com.found_404.funco.favoritecoin.batch.reader;

import static com.found_404.funco.favoritecoin.batch.exception.FavoriteCoinBatchErrorCode.*;
import static com.found_404.funco.global.type.RedisZSetType.*;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import com.found_404.funco.favoritecoin.batch.exception.FavoriteCoinBatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FavoriteCoinReader implements ItemStreamReader<String> {
	private final RedisTemplate<String, Object> favoriteCoinZSetRedisTemplate;
	private AtomicInteger index;
	private ZSetOperations<String, Object> zSetOperations;
	private static Long size;

	public FavoriteCoinReader(RedisTemplate<String, Object> favoriteCoinZSetRedisTemplate) {
		this.favoriteCoinZSetRedisTemplate = favoriteCoinZSetRedisTemplate;
		index = new AtomicInteger(0);
	}

	public void open(ExecutionContext executionContext) throws ItemStreamException {
		zSetOperations = favoriteCoinZSetRedisTemplate.opsForZSet();
		size = zSetOperations.size(FAVORITE_COIN_ZSET.name());
	}

	@Override
	public String read() {
		log.info("=================== read() operates ===================");
		if (index.get() < size) {
			return Objects.requireNonNull(Objects.requireNonNull(zSetOperations
						.range(FAVORITE_COIN_ZSET.name(), index.get(), index.getAndIncrement()))
					.stream()
					.findFirst()
					.orElseThrow(() -> new FavoriteCoinBatchException(NOT_FOUND_CONTENT)))
				.toString();
		}
		return null;
	}
}
