package com.found_404.funco.global.security.service;

import static com.found_404.funco.global.security.exception.SecurityErrorCode.*;
import static java.util.concurrent.TimeUnit.*;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.found_404.funco.auth.dto.MemberDto;
import com.found_404.funco.global.security.exception.SecurityException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {

	@Value("${spring.jwt.token.secret-key}")
	private String secretKey;

	@Value("${spring.jwt.token.refresh-secret-key}")
	private String refreshSecretKey;

	private final RedisTemplate<String, Object> tokenRedisTemplate;
	private final long TOKEN_PERIOD = 12 * 60 * 60 * 1000L; // 12h
	private final long REFRESH_PERIOD = 14 * 24 * 60 * 60 * 1000L; // 14일
	private final String REDIS_REFRESH_TOKEN_KEY = "refreshToken";

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
	}

	public String createToken(Long memberId) {
		Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));
		Date now = new Date();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + TOKEN_PERIOD))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public String createRefreshToken(MemberDto memberDto) {
		Claims claims = Jwts.claims().setSubject(String.valueOf(memberDto.oauthId().getOauthServerId()));
		Date now = new Date();

		String refreshToken = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + REFRESH_PERIOD))
			.signWith(SignatureAlgorithm.HS256, refreshSecretKey)
			.compact();

		// redis refreshToken 저장
		HashOperations<String, Object, Object> hashOperations = tokenRedisTemplate.opsForHash();
		hashOperations.put(memberDto.oauthId().getOauthServerId(), REDIS_REFRESH_TOKEN_KEY, refreshToken);
		tokenRedisTemplate.expire(memberDto.oauthId().getOauthServerId(), REFRESH_PERIOD, MILLISECONDS);
		return refreshToken;
	}

	public String resolveToken(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");

		if (accessToken == null || accessToken.trim().isEmpty()) {
			throw new SecurityException(EMPTY_TOKEN, HttpStatus.UNAUTHORIZED);
		}

		return accessToken;
	}

	public boolean validateToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
			return claims.getExpiration().after(new Date());

		} catch (ExpiredJwtException e) {
			return false;
		}
	}

	// public TokenResponse reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
	// 	try {
	// 		String refreshToken = readRefreshToken(request);
	// 		String oauthServerId = readMemberIdFromRefreshToken(refreshToken);
	// 		String redisRefreshToken = Objects.requireNonNull(
	// 			tokenRedisTemplate.opsForHash().get(oauthServerId, REDIS_REFRESH_TOKEN_KEY)).toString();
	//
	// 		MemberDto member = memberServiceClient.getAuthMember(new OauthId(oauthServerId, GOOGLE))
	// 			.orElseThrow(() -> new SecurityException(MEMBER_NOT_FOUND, HttpStatus.UNAUTHORIZED));
	//
	// 		if (!redisRefreshToken.equals(refreshToken)) {// 리프레시 토큰 만료 시 헤더에서 삭제
	// 			deleteHeader(response);
	// 			throw new SecurityException(EXPIRED_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED);
	// 		}
	//
	// 		return TokenResponse.builder().accessToken(createToken(member)).build();
	//
	// 	} catch (NullPointerException e) {
	// 		deleteHeader(response);
	// 		throw new SecurityException(EXPIRED_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED);
	// 	}
	// }

	public Long readMemberId(String token) {
		String memberId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		return Long.valueOf(memberId);
	}

	public String readMemberIdFromRefreshToken(String refreshToken) {
		return Jwts.parser()
			.setSigningKey(refreshSecretKey)
			.parseClaimsJws(refreshToken)
			.getBody()
			.getSubject();
	}

	private String readRefreshToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("refreshToken")) {
				return cookie.getValue().trim();
			}
		}
		return null;
	}

	// access, refresh token 정보 삭제
	public void deleteHeader(HttpServletResponse response) {
		response.setHeader("Authorization", null);
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}
