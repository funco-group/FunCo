package com.found_404.funco.favoritecoin.batch.listener;

import static com.found_404.funco.global.type.RedisZSetType.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JobExecutionLogger implements JobExecutionListener {
	private final RedisTemplate<String, Object> favoriteCoinRedisTemplate;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("Job Start -> {}", jobExecution.getJobInstance().getJobName());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		refreshZSetAfterScheduling();
		log.info("Job End -> {} , job result status : {} ", jobExecution.getJobInstance().getJobName(),
			jobExecution.getStatus());
	}

	// 전날 데이터 삭제
	public void refreshZSetAfterScheduling() {
		LocalDateTime startOfYesterday = LocalDateTime.now().minusDays(1).with(LocalTime.MIN);
		LocalDateTime endOfYesterday = LocalDateTime.now().minusDays(1).with(LocalTime.MAX);
		favoriteCoinRedisTemplate.opsForZSet()
			.removeRangeByScore(FAVORITE_COIN_ZSET.name(), startOfYesterday.toEpochSecond(
				ZoneOffset.UTC), endOfYesterday.toEpochSecond(ZoneOffset.UTC));
	}
}

