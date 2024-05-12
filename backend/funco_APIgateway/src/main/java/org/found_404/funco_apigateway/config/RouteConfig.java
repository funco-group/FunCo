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
	private final String EUREKA_MEMBER = "lb://MEMBER-SERVICE";
	private final JwtAuthenticationGatewayFilterFactory jwtAuthentication;

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
			// Member Service API
			.route(r -> r.path("/api/v1/auth/**")
				.uri(EUREKA_MEMBER))
			.route(r -> r.path("/api/v1/member/**")
				.filters(f -> f.filter(jwtAuthentication.apply()))
				.uri(EUREKA_MEMBER))
			.route(r -> r.path("/api/v1/hello")
				.uri(EUREKA_MEMBER))
			.build();
	}
}