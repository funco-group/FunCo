package com.found_404.funcomember.feignClient.service;

import com.found_404.funcomember.feignClient.client.FollowServiceClient;
import com.found_404.funcomember.member.exception.MemberException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.found_404.funcomember.member.exception.MemberErrorCode.FOLLOW_SERVER_ERROR;

@RequiredArgsConstructor
@Service
@Slf4j
public class FollowService {
	private final FollowServiceClient followServiceClient;
	private final String SERVER_NAME = "[follow-service]";

	public Long getInvestments(Long memberId) {
		try {
			return followServiceClient.getInvestments(memberId).investments();
		} catch (FeignException e) {
			log.error("{} get investments error : {}", SERVER_NAME, e.getMessage());
			throw new MemberException(FOLLOW_SERVER_ERROR);
		}
	}

}
