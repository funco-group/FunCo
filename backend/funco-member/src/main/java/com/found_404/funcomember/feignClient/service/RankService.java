package com.found_404.funcomember.feignClient.service;

import static com.found_404.funcomember.member.exception.MemberErrorCode.*;

import org.springframework.stereotype.Service;

import com.found_404.funcomember.feignClient.client.RankServiceClient;
import com.found_404.funcomember.feignClient.dto.RankingResponse;
import com.found_404.funcomember.member.exception.MemberException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class RankService {
	private final RankServiceClient rankServiceClient;
	private final String SERVER_NAME = "[rank-service]";

	public RankingResponse getRankingInfo(String type, Long memberId) {
		try {
			return rankServiceClient.getRankingInfo(type, memberId);
		} catch (FeignException e) {
			log.error("{} get rankingInfo error : {}", SERVER_NAME, e.getMessage());
			throw new MemberException(RANK_SERVER_ERROR);
		}
	}
}
