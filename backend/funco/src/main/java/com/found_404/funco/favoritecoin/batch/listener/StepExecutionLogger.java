package com.found_404.funco.favoritecoin.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StepExecutionLogger implements StepExecutionListener {
	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("Job Start -> {}", stepExecution.getStepName());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("Job End -> {} , job result status : {} ", stepExecution.getStepName(), stepExecution.getStatus());
		return stepExecution.getExitStatus();
	}
}
