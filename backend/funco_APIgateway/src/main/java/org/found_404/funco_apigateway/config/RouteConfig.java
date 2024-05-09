package org.found_404.funco_apigateway.config;

import org.found_404.funco_apigateway.security.filter.JwtAuthenticationGatewayFilterFactory;
import org.found_404.funco_apigateway.security.service.TokenService;
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
			.route(r -> r.path("/api/v1/hello/**")
				.filters(f -> f.filter(new JwtAuthenticationGatewayFilterFactory(tokenService).apply(
					new JwtAuthenticationGatewayFilterFactory.Config("Authorization", "Bearer"))))
				.uri("https://main-api.funco.co.kr"))
			.route(r -> r.path("/api/v1/asset/**")
				.filters(f -> f.filter(new JwtAuthenticationGatewayFilterFactory(tokenService).apply(
					new JwtAuthenticationGatewayFilterFactory.Config("Authorization", "Bearer"))))
				.uri("https://main-api.funco.co.kr"))
			.route(r -> r.path("/user/**")
				.filters(f -> f.addRequestHeader("member-id","1"))
				.uri("lb://MSA-USER-SERVICE")) // eureka를 사용하는 경우 lb(loadbalancing)://유레카에서뜨는서비스이름
			.build();
	}
}