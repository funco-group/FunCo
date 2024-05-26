package com.found_404.funco.feignClient.service;

import static com.found_404.funco.trade.exception.TradeErrorCode.*;

import org.springframework.stereotype.Service;

import com.found_404.funco.feignClient.client.MemberServiceClient;
import com.found_404.funco.feignClient.dto.UpdateCash;
import com.found_404.funco.trade.exception.TradeException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberServiceClient memberServiceClient;
	private final String SERVER_NAME = "[member-service]";

	public Long updateMemberCash(Long memberId, Long cash) {
		try {
			log.info("[API PATCH] member server cash");
			return memberServiceClient.updateCash(memberId, new UpdateCash(cash)).cash();
		} catch (FeignException e) {
			log.error("{} update cash error : {}", SERVER_NAME, e.getMessage());
			throw new TradeException(INSUFFICIENT_ASSET);
		}
	}

	public Long getMemberCash(Long memberId) {
		try {
			log.info("[API GET] member server cash");
			return memberServiceClient.getMemberCash(memberId).cash();
		} catch (FeignException e) {
			log.error("{} get cash error : {}", SERVER_NAME, e.getMessage());
			throw new TradeException(MEMBER_SERVER_ERROR);
		}
	}


}
