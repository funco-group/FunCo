package org.found_404.funco_apigateway.config;

import org.found_404.funco_apigateway.filter.JwtAuthenticationGatewayFilterFactory;
import org.found_404.funco_apigateway.service.TokenService;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class RouteConfig {

	private final TokenService tokenService;

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
			// Member Service API
			.route(r -> r.path("/api/v1/auth/**")
				.uri("https://member.funco.co.kr"))
			.route(r -> r.path("/api/v1/member/**")
				.filters(f -> f.filter(new JwtAuthenticationGatewayFilterFactory(tokenService).apply(
					new JwtAuthenticationGatewayFilterFactory.Config("Authorization", "Bearer"))))
				.uri("https://member.funco.co.kr"))
			.route(r -> r.path("/api/v1/hello")
				.uri("https://member.funco.co.kr"))
			.build();
	}
}