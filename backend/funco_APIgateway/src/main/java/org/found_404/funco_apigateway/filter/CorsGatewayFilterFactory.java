package org.found_404.funco_apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CorsGatewayFilterFactory extends AbstractGatewayFilterFactory<CorsGatewayFilterFactory.Config> {

	public CorsGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			if (CorsUtils.isCorsRequest(exchange.getRequest())) {
				return handleCorsRequest(exchange, config);
			}
			return chain.filter(exchange);
		};
	}

	private Mono<Void> handleCorsRequest(ServerWebExchange exchange, Config config) {
		exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", config.allowedOrigin);
		exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", config.allowedMethods);
		exchange.getResponse().getHeaders().add("Access-Control-Max-Age", config.maxAge);
		exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", config.allowedHeaders);
		exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", config.allowCredentials);
		exchange.getResponse().getHeaders().add("Access-Control-Expose-Headers", config.exposeHeaders);

		if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
			exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", config.allowedOrigin);
			exchange.getResponse().setStatusCode(HttpStatus.OK);
			return Mono.empty();
		}
		return exchange.getResponse().setComplete();
	}

	public static class Config {
		public String allowedOrigin = "https://funco.co.kr";
		public String allowedMethods = "GET, PUT, POST, DELETE, OPTIONS";
		public String maxAge = "3600";
		public String allowedHeaders = "X-Requested-With, Authorization, Access-Control-Allow-Origin, Content-Type";
		public String allowCredentials = "true";
		public String exposeHeaders = "*, Authorization, Refreshtoken, authorization, refreshtoken";
	}
}