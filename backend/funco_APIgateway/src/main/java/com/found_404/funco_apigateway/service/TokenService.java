package com.found_404.funco_apigateway.service;

import static com.found_404.funco_apigateway.exception.TokenErrorCode.*;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.found_404.funco_apigateway.exception.TokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenService {

	@Value("${spring.jwt.token.secret-key}")
	private String secretKey;

	@Value("${spring.jwt.token.refresh-secret-key}")
	private String refreshSecretKey;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
	}

	public String validateToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

			Date expirationDate = claims.getExpiration();
			Date now = new Date();

			if (expirationDate.before(now)) {
				throw new TokenException(EXPIRED_TOKEN, HttpStatus.UNAUTHORIZED);
			}

			return claims.getSubject();

		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException e) {
			throw new TokenException(INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
		} catch (ExpiredJwtException e) {
			throw new TokenException(EXPIRED_TOKEN, HttpStatus.UNAUTHORIZED);
		} catch (IllegalArgumentException e) {
			throw new TokenException(SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
