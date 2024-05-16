package com.found_404.funco_apigateway.filter;

import static java.lang.Boolean.*;

import java.util.Arrays;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.found_404.funco_apigateway.exception.TokenException;
import com.found_404.funco_apigateway.service.TokenService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAuthenticationGatewayFilterFactory extends
	AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {
	private static final String DEFAULT_HEADER = "Authorization", DEFAULT_GRANTED = "Bearer";

	private final TokenService tokenService;

	public JwtAuthenticationGatewayFilterFactory(TokenService tokenService) {
		super(Config.class);
		this.tokenService = tokenService;
	}

	public GatewayFilter apply() {
		return apply(new Config(DEFAULT_HEADER, DEFAULT_GRANTED, TRUE));
	}

	public GatewayFilter apply(Boolean authenticated) {
		return apply(new Config(DEFAULT_HEADER, DEFAULT_GRANTED, authenticated));
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String authorizationHeader = exchange.getRequest().getHeaders().getFirst(config.headerName);

			// 예외 플래그 변수
			boolean invalidToken = false;

			if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(config.granted + " ")) {
				String token = authorizationHeader.substring(7);
				try {
					String memberId = tokenService.validateToken(token);
					exchange.getRequest().mutate().header("X-Member-ID", memberId);
				} catch (TokenException e) {
					log.error("토큰 에러 : {}", e.getErrorMessage());
					invalidToken = true;
				} catch (Exception e) {
					log.error(Arrays.toString(e.getStackTrace()));
					invalidToken = true;
				}
			} else {
				invalidToken = true;
			}

			if (invalidToken && config.authenticated) {
				return unauthorizedResponse(exchange);
			}
			return chain.filter(exchange);
		};
	}

	private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	}

	@Getter
	@Setter
	public static class Config {
		private String headerName;
		private String granted;
		private Boolean authenticated;

		public Config(String headerName, String granted, Boolean authenticated) {
			this.headerName = headerName;
			this.granted = granted;
			this.authenticated = authenticated;
		}
	}
}
