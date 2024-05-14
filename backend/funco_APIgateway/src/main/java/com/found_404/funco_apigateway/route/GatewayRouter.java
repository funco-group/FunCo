package com.found_404.funco_apigateway.route;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.found_404.funco_apigateway.filter.JwtAuthenticationGatewayFilterFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class GatewayRouter {
	private final String EUREKA_AUTH = "lb://AUTH-SERVICE";
	private final String EUREKA_MEMBER = "lb://MEMBER-SERVICE";
	private final String EUREKA_TRADE = "lb://TRADE-SERVICE";
	private final String EUREKA_STATISTICS = "lb://STATISTICS-SERVICE";
	private final String EUREKA_FOLLOW = "lb://FOLLOW-SERVICE";

	private final JwtAuthenticationGatewayFilterFactory jwtAuthentication;

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
			.route(r -> r.path("/api/v1/auth/**").uri(EUREKA_AUTH))
			.route(r -> getJwtFilterRoute(r, "/api/v1/members/**", EUREKA_MEMBER))
			.route(r -> getJwtFilterRoute(r, "/api/v1/trade/**", EUREKA_TRADE))
			.route(r -> getJwtFilterRoute(r, "/api/v1/follows/**", EUREKA_FOLLOW))
			.route(r -> getJwtFilterRoute(r, "/api/v1/statistics/**", EUREKA_STATISTICS))
			.build();
	}

	private Buildable<Route> getJwtFilterRoute(PredicateSpec r, String x, String EUREKA_MEMBER) {
		return r.path(x)
			.filters(f -> f.filter(jwtAuthentication.apply()))
			.uri(EUREKA_MEMBER);
	}
}
