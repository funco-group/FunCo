package com.found_404.funco.auth.service;

import java.util.Optional;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.found_404.funco.auth.client.MemberServiceClient;
import com.found_404.funco.auth.client.OauthMemberClientComposite;
import com.found_404.funco.auth.dto.OauthDto;
import com.found_404.funco.auth.dto.request.OAuthMemberRequest;
import com.found_404.funco.auth.dto.response.LoginResponse;
import com.found_404.funco.auth.dto.response.OAuthMemberResponse;
import com.found_404.funco.auth.type.OauthServerType;
import com.found_404.funco.global.security.service.TokenService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final TokenService tokenService;

	private final OauthMemberClientComposite oauthMemberClientComposite;
	private final RedisTemplate<String, Object> tokenRedisTemplate;
	private final MemberServiceClient memberServiceClient;
	// private final NotificationService notificationService;

	private final long INIT_CASH = 10_000_000;

	// 가입되어 있지 않다면 저장(회원가입)
	public LoginResponse login(HttpServletResponse response, OauthServerType oauthServerType, String authCode) {
		OauthDto dto = oauthMemberClientComposite.fetch(oauthServerType, authCode);
		OAuthMemberResponse oAuthMemberResponse = Optional.of(memberServiceClient.getAuthMember(
			dto.memberDto().oauthId().getOauthServerType().name(),
			dto.memberDto().oauthId().getOauthServerId())).orElseGet(() -> {
			OAuthMemberRequest oAuthMemberRequest = OAuthMemberRequest.builder()
				.oauthId(dto.memberDto().oauthId())
				.nickname(dto.memberDto().nickname())
				.profileUrl(dto.memberDto().profileUrl())
				.build();
			// Member 엔티티 만들라고 보내는 요청 로직 구현
			// 구현해야됨!!
			return memberServiceClient.addAuthMember(oAuthMemberRequest);
		});

		// redis oauthAccessToken 저장
		// 추후 로그아웃 구현 시 필요할 수 있음
		HashOperations<String, Object, Object> hashOperations = tokenRedisTemplate.opsForHash();
		hashOperations.put(dto.memberDto().oauthId().getOauthServerId(), "oauthAccessToken", dto.accessToken());

		String refreshToken = tokenService.createRefreshToken(dto.memberDto());
		// 기존의 쿠키 설정을 문자열로 변환
		String cookieValue = "refreshToken=" + refreshToken +
			"; HttpOnly; Secure; Path=/; SameSite=None";

		// 응답 헤더에 쿠키 추가
		response.addHeader("Set-Cookie", cookieValue);

		return LoginResponse.builder()
			.profileUrl(oAuthMemberResponse.profileUrl())
			.nickname(oAuthMemberResponse.nickname())
			.memberId(oAuthMemberResponse.memberId())
			.accessToken(tokenService.createToken(oAuthMemberResponse.memberId()))
			// .unReadCount(notificationService.getUnReadCount(member.getId()))
			.build();
	}

	// public TokenResponse reissueToken(HttpServletRequest request, HttpServletResponse response) {
	//
	// 	// 유효하다면 accessToken 재발급
	// 	// 기존 코드 Refactoring 필요
	// 	return tokenService.reissueAccessToken(request, response);
	// }

}
