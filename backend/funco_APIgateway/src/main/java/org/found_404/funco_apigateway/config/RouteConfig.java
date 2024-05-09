package org.found_404.funco_apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

	// 테스트용 로컬 라우터
	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
			.route(r -> r.path("/user/**")
				.filters(f -> f.addRequestHeader("Authorization ","Bearer "+"access token"))
				.uri("http://localhost:8081"))
			.route(r -> r.path("/user/**")
				.filters(f -> f.addRequestHeader("member-id","1"))
				.uri("lb://MSA-USER-SERVICE")) // eureka를 사용하는 경우 lb(loadbalancing)://유레카에서뜨는서비스이름
			.build();
	}



}
