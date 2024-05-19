package com.found_404.funco_apigateway.route;

import static java.lang.Boolean.*;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

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
	private final String EUREKA_NOTIFICATION = "lb://NOTIFICATION-SERVICE";
	private final String EUREKA_RANK = "lb://RANK-SERVICE";
	private final String EUREKA_NOTE = "lb://NOTE-SERVICE";
	private final String EUREKA_ASSET = "lb://ASSET-SERVICE";

	private final JwtAuthenticationGatewayFilterFactory jwtAuthentication;

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/api/v1/auth/*/signin").filters(f -> circuitBreaker(f, EUREKA_AUTH)).uri(EUREKA_AUTH))
				.route(r -> r.path("/api/v1/rank/**").filters(f -> circuitBreaker(f, EUREKA_RANK)).uri(EUREKA_RANK))

				.route(r -> getJwtFilterRoute(r, "/api/v1/members/**", EUREKA_MEMBER))
				.route(r -> r.path("/api/v2/members/**").filters(f -> circuitBreaker(f.filter(jwtAuthentication.apply(FALSE)), EUREKA_MEMBER))
						.uri(EUREKA_MEMBER))

				.route(r -> getJwtFilterRoute(r, "/api/v1/crypto/favorite/**", EUREKA_MEMBER))

				.route(r -> getJwtFilterRoute(r, "/api/v1/trade/**", EUREKA_TRADE))
				.route(r -> getJwtFilterRoute(r, "/api/v1/follows/**", EUREKA_FOLLOW))
				.route(r -> getJwtFilterRoute(r, "/api/v1/notifications/**", EUREKA_NOTIFICATION))
				.route(r -> getJwtFilterRoute(r, "/api/v1/comments/**", EUREKA_NOTE))
				.route(r -> getJwtFilterRoute(r, "/api/v1/assets/**", EUREKA_ASSET))

				.route(r -> r.path("/api/v1/notes/**").and()
						.method(HttpMethod.GET).filters(f -> circuitBreaker(f.filter(jwtAuthentication.apply(FALSE)), EUREKA_NOTE)).uri(EUREKA_NOTE))

				.route(r -> r.path("/api/v1/notes/**").and()
						.method(HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.PUT)
						.filters(f -> circuitBreaker(f.filter(jwtAuthentication.apply()), EUREKA_NOTE)).uri(EUREKA_NOTE))

				.route(r -> getJwtFilterRoute(r, "/api/v1/statistics/**", EUREKA_STATISTICS))
				.build();
	}

	private GatewayFilterSpec circuitBreaker(GatewayFilterSpec filter, String name) {
		return filter.circuitBreaker(c -> c.setName(name + "-circuitBreaker")
				.setFallbackUri("forward:/fallback"));
	}

	private Buildable<Route> getJwtFilterRoute(PredicateSpec r, String x, String url) {
		return r.path(x)
				.filters(f -> circuitBreaker(f.filter(jwtAuthentication.apply()), url))
				.uri(url);
	}
}
