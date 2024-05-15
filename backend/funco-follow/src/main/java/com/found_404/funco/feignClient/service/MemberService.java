package com.found_404.funco.feignClient.service;

import static com.found_404.funco.follow.exception.FollowErrorCode.*;

import org.springframework.stereotype.Service;

import com.found_404.funco.feignClient.client.MemberServiceClient;
import com.found_404.funco.feignClient.dto.UpdateCash;
import com.found_404.funco.follow.exception.FollowException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberServiceClient memberServiceClient;
	private final String SERVER_NAME = "[member-service]";

	public void updateMemberCash(Long memberId, Long cash) {
		try {
			memberServiceClient.updateCash(memberId, new UpdateCash(cash));
		} catch (FeignException e) {
			log.error("{} update cash : {}", SERVER_NAME, e.getMessage());
			throw new FollowException(INSUFFICIENT_ASSET);
		}
	}

	public Long getMemberCash(Long memberId) {
		try {
			return memberServiceClient.getMemberCash(memberId).cash();
		} catch (FeignException e) {
			log.error("{} get cash : {}", SERVER_NAME, e.getMessage());
			throw new FollowException(INSUFFICIENT_ASSET);
		}
	}


}
