package com.found_404.funco.favoritecoin.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.found_404.funco.favoritecoin.batch.dto.FavoriteCoinBatchInfo;
import com.found_404.funco.favoritecoin.batch.listener.JobExecutionLogger;
import com.found_404.funco.favoritecoin.batch.listener.StepExecutionLogger;
import com.found_404.funco.favoritecoin.batch.processor.FavoriteCoinProcessor;
import com.found_404.funco.favoritecoin.batch.reader.FavoriteCoinReader;
import com.found_404.funco.favoritecoin.batch.writer.FavoriteCoinWriter;
import com.found_404.funco.favoritecoin.domain.repository.FavoriteCoinRepository;
import com.found_404.funco.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FavoriteCoinBatchConfig {

	private final RedisTemplate<String, Object> favoriteCoinRedisTemplate;
	private final RedisTemplate<String, Object> favoriteCoinZSetRedisTemplate;
	private final FavoriteCoinRepository favoriteCoinRepository;
	private final MemberRepository memberRepository;
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final JobExecutionLogger jobExecutionLogger;
	private final StepExecutionLogger stepExecutionLogger;
	private static final int CHUNK_SIZE = 2;

	@Bean
	public Job updateFavoriteCoinsJob() {
		return new JobBuilder("updateFavoriteCoinsJob", jobRepository)
			.incrementer(new RunIdIncrementer())
			.start(updateFavoriteCoinsStep())
			.listener(jobExecutionLogger)
			.build();
	}

	@Bean
	@JobScope
	public Step updateFavoriteCoinsStep() {
		return new StepBuilder("updateFavoriteCoinsStep", jobRepository)
			.<String, FavoriteCoinBatchInfo>chunk(CHUNK_SIZE, transactionManager)
			.reader(favoriteCoinReader())
			.processor(favoriteCoinProcessor())
			.writer(favoriteCoinWriter())
			.listener(stepExecutionLogger)
			.build();
	}

	@Bean
	@StepScope
	public FavoriteCoinReader favoriteCoinReader() {
		log.info("=================== favoriteCoinReader() operates ===================");
		return new FavoriteCoinReader(favoriteCoinZSetRedisTemplate);
	}

	@Bean
	@StepScope
	public FavoriteCoinProcessor favoriteCoinProcessor() {
		log.info("=================== favoriteCoinProcessor() operates ===================");
		return new FavoriteCoinProcessor(favoriteCoinRedisTemplate);
	}

	@Bean
	@StepScope
	public FavoriteCoinWriter favoriteCoinWriter() {
		log.info("=================== favoriteCoinWriter() operates ===================");
		return new FavoriteCoinWriter(favoriteCoinRedisTemplate, favoriteCoinRepository, memberRepository);
	}
}
