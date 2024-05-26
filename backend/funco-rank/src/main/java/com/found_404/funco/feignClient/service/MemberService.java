package com.found_404.funco.feignClient.service;

import static com.found_404.funco.rank.exception.RankErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.found_404.funco.feignClient.client.MemberServiceClient;
import com.found_404.funco.feignClient.dto.RankMemberInfo;
import com.found_404.funco.rank.exception.RankException;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberServiceClient memberServiceClient;

	public List<RankMemberInfo> getAllMemberList() {
		try {
			return memberServiceClient.getAllMemberList();
		} catch (FeignException e) {
			log.error("member client error : {}", e.getMessage());
			throw new RankException(RANK_NOT_FOUND);
		}
	}
}
