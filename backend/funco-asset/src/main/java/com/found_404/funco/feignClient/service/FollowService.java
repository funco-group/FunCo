package com.found_404.funco.feignClient.service;

import static com.found_404.funco.asset.exception.AssetErrorCode.*;

import org.springframework.stereotype.Service;

import com.found_404.funco.asset.exception.AssetException;
import com.found_404.funco.feignClient.client.FollowServiceClient;
import com.found_404.funco.feignClient.dto.InvestmentsResponse;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowService {

	private final FollowServiceClient followServiceClient;
	private final String SERVER_NAME = "[follow-service]";

	public void modifyFollowingAndFollower(Long memberId) {
		try {
			followServiceClient.modifyFollowingAndFollower(memberId);
		} catch (FeignException e) {
			log.error("{} Modify Following And Follower error : {}", SERVER_NAME, e.getMessage());
			throw new AssetException(FOLLOW_SERVER_ERROR);
		}
	}

	public InvestmentsResponse getFollowingInvestment(Long memberId) {
		try {
			return followServiceClient.getFollowingInvestment(memberId);
		} catch (FeignException e) {
			log.error("{} Get Following Investment error : {}", SERVER_NAME, e.getMessage());
			throw new AssetException(FOLLOW_SERVER_ERROR);
		}
	}
}
