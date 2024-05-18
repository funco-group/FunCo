package com.found_404.funco.feignClient.service;

import static com.found_404.funco.asset.exception.AssetErrorCode.*;

import org.springframework.stereotype.Service;

import com.found_404.funco.asset.exception.AssetException;
import com.found_404.funco.feignClient.client.MemberServiceClient;
import com.found_404.funco.feignClient.dto.MemberInitCashDate;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberServiceClient memberServiceClient;
	private final String SERVER_NAME = "[member-service]";

	public MemberInitCashDate getInitCashDate(Long memberId) {
		try {
			return memberServiceClient.getInitCashDate(memberId);
		} catch (FeignException e) {
			log.error("{} Get Init Cash Date error : {}", SERVER_NAME, e.getMessage());
			throw new AssetException(MEMBER_SERVER_ERROR);
		}
	}

	public void modifyCashAndInitCashDate(Long memberId) {
		try {
			memberServiceClient.modifyCashAndInitCashDate(memberId);
		} catch (FeignException e) {
			log.error("{} Modify Cash And Init Cash Date error : {}", SERVER_NAME, e.getMessage());
			throw new AssetException(MEMBER_SERVER_ERROR);
		}
	}
}
