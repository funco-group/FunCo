package com.found_404.funco.favoritecoin.batch.scheduler;

import static com.found_404.funco.favoritecoin.batch.exception.FavoriteCoinBatchErrorCode.*;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.found_404.funco.favoritecoin.batch.exception.FavoriteCoinBatchException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FavoriteCoinBatchScheduler {

	private final Job updateFavoriteCoinsJob;
	private final JobLauncher jobLauncher;

	@Scheduled(cron = "0 0 7 * * *", zone = "Asia/Seoul")
	private void regularBatchJob() {
		log.info("info log = {}", "=================== 7시에 동기화 스케줄러 실행 ===================");
		log.info("info log = {}", "=================== 동기화 작업 시작 ===================");
		JobParameters jobParameters = new JobParametersBuilder()
			.addDate("date", new Date())
			.toJobParameters();
		try {
			jobLauncher.run(updateFavoriteCoinsJob, jobParameters);
		} catch (Exception e) {
			log.error("error log = {}", e.getStackTrace());
			throw new FavoriteCoinBatchException(BATCH_FAILED);
		}
		log.info("info log = {}", "=================== 동기화 작업 종료 ===================");
	}
}