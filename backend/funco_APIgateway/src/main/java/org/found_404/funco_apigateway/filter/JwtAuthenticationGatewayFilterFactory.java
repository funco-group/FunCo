package org.found_404.funco_apigateway.filter;

import java.util.Arrays;

import org.found_404.funco_apigateway.exception.TokenException;
import org.found_404.funco_apigateway.service.TokenService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAuthenticationGatewayFilterFactory extends
	AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

	private TokenService tokenService;

	public JwtAuthenticationGatewayFilterFactory(TokenService tokenService) {
		super(Config.class);
		this.tokenService = tokenService;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String authorizationHeader = exchange.getRequest().getHeaders().getFirst(config.headerName);
			if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(config.granted + " ")) {
				String token = authorizationHeader.substring(7);
				try {
					String memberId = tokenService.validateToken(token);
					exchange.getRequest().mutate().header("X-Member-ID", memberId);
					return chain.filter(exchange);
				} catch (TokenException e) {
					log.error("토큰 에러 : {}", e.getErrorMessage());
					return unauthorizedResponse(exchange);
				} catch (Exception e) {
					log.error(Arrays.toString(e.getStackTrace()));
					return unauthorizedResponse(exchange);
				}
			}
			return unauthorizedResponse(exchange);
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

		public Config(String headerName, String granted) {
			this.headerName = headerName;
			this.granted = granted;
		}
	}
}
