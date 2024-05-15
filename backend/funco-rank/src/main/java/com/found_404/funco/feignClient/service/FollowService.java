package com.found_404.funco.feignClient.service;

import static com.found_404.funco.rank.exception.RankErrorCode.*;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.found_404.funco.feignClient.client.FollowServiceClient;
import com.found_404.funco.rank.exception.RankException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowService {
	private final FollowServiceClient followServiceClient;

	public Map<Long, Long> getFollowerInvestmentList() {
		try {
			return followServiceClient.getFollowerInvestmentList();
		} catch (FeignException e) {
			log.error("follow client error : {}", e.getMessage());
			throw new RankException(RANK_NOT_FOUND);
		}
	}

	public Map<Long, Long> getFollowingInvestmentList() {
		try {
			return followServiceClient.getFollowingInvestmentList();
		} catch (FeignException e) {
			log.error("follow client error : {}", e.getMessage());
			throw new RankException(RANK_NOT_FOUND);
		}
	}

}
