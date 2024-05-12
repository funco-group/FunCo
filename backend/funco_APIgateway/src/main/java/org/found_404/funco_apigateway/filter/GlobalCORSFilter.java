package org.found_404.funco_apigateway.filter;

import static org.springframework.http.HttpHeaders.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalCORSFilter {

	private static final List<String> ALLOWED_ORIGINS = List.of("https://www.funco.co.kr", "https://funco.co.kr");
	private static final String ALLOWED_HEADERS = "x-requested-with, Authorization, Content-Type";
	private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
	private static final String MAX_AGE = "3600";
	private static final String ALLOWED_CREDENTIALS = "true";

	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			ServerHttpRequest request = ctx.getRequest();
			String requestOrigin = request.getHeaders().getOrigin();

			if (CorsUtils.isPreFlightRequest(request)) {
				ServerHttpResponse response = ctx.getResponse();
				HttpHeaders headers = response.getHeaders();

				if (ALLOWED_ORIGINS.contains(requestOrigin)) {
					headers.set(ACCESS_CONTROL_ALLOW_ORIGIN, requestOrigin);
				}

				headers.add(ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
				headers.add(ACCESS_CONTROL_MAX_AGE, MAX_AGE);
				headers.add(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
				headers.add(ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOWED_CREDENTIALS);

				if (request.getMethod() == HttpMethod.OPTIONS) {
					response.setStatusCode(HttpStatus.OK);
					return Mono.empty();
				}
			}
			return chain.filter(ctx);
		};
	}
}