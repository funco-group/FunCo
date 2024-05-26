package com.found_404.funco.feignClient.service;

import static com.found_404.funco.auth.exception.AuthErrorCode.*;

import org.springframework.stereotype.Service;

import com.found_404.funco.auth.exception.AuthException;
import com.found_404.funco.feignClient.client.MemberServiceClient;
import com.found_404.funco.feignClient.dto.response.OAuthIdResponse;
import com.found_404.funco.feignClient.dto.response.OAuthMemberResponse;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
	private final MemberServiceClient memberServiceClient;

	private final String SERVER_NAME = "[member-service]";

	public OAuthMemberResponse getAuthMember(String provider, String oauthId) {
		try {
			return memberServiceClient.getAuthMember(provider, oauthId);
		} catch (FeignException e) {
			log.error("{} get Auth Member error : {}", SERVER_NAME, e.getMessage());
			throw new AuthException(MEMBER_SERVER_ERROR);
		}
	}

	public OAuthIdResponse getOAuthId(Long memberId) {
		try {
			return memberServiceClient.getOAuthId(memberId);
		} catch (FeignException e) {
			log.error("{} get OAuth ID error : {}", SERVER_NAME, e.getMessage());
			throw new AuthException(MEMBER_SERVER_ERROR);
		}
	}
}
