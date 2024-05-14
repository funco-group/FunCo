package com.found_404.funco.client.service;

import static com.found_404.funco.trade.exception.TradeErrorCode.*;

import org.springframework.stereotype.Service;

import com.found_404.funco.client.MemberServiceClient;
import com.found_404.funco.client.dto.UpdateCash;
import com.found_404.funco.trade.exception.TradeException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberServiceClient memberServiceClient;

	public void updateMemberCash(Long memberId, Long cash) {
		try {
			memberServiceClient.updateCash(memberId, new UpdateCash(cash));
		} catch (FeignException e) {
			log.error("member client error : {}", e.getMessage());
			throw new TradeException(INSUFFICIENT_ASSET);
		}
	}

	public Long getMemberCash(Long memberId) {
		try {
			return memberServiceClient.getMemberCash(memberId).cash();
		} catch (FeignException e) {
			log.error("member client error : {}", e.getMessage());
			throw new TradeException(INSUFFICIENT_ASSET);
		}
	}


}
