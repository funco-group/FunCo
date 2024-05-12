package org.found_404.funco_apigateway.filter;

import static org.springframework.http.HttpHeaders.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalCORSFilter {

	private static final List<String> ALLOWED_ORIGINS = new ArrayList<>(List.of("https://www.funco.co.kr", "https://funco.co.kr"));
	private static final String ALLOWED_HEADERS = "X-Requested-With, Authorization, Content-Type";
	private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
	private static final String MAX_AGE = "3600";
	private static final String ALLOWED_CREDENTIALS = "true";

	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange exchange, WebFilterChain chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			String requestOrigin = request.getHeaders().getOrigin();

			if (ALLOWED_ORIGINS.contains(requestOrigin)) {
				response.getHeaders().set(ACCESS_CONTROL_ALLOW_ORIGIN, requestOrigin);
				response.getHeaders().set(ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
				response.getHeaders().set(ACCESS_CONTROL_MAX_AGE, MAX_AGE);
				response.getHeaders().set(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
				response.getHeaders().set(ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOWED_CREDENTIALS);
			}

			if (request.getMethod() == HttpMethod.OPTIONS) {
				response.setStatusCode(HttpStatus.OK);
				return Mono.empty();
			}

			return chain.filter(exchange);
		};
	}
}
