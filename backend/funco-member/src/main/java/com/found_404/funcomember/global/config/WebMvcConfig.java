package com.found_404.funcomember.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.found_404.funcomember.global.util.OauthServerTypeConverter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	// private final AuthMemberIdArgumentResolver authMemberIdArgumentResolver;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			.allowedOrigins("https://funco.co.kr", "https://api.funco.co.kr")
			.allowedHeaders("Content-Type", "Authorization") // Authorization 헤더 허용
			.allowCredentials(true)
			.maxAge(3600)
			.exposedHeaders("*");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new OauthServerTypeConverter());
	}

	// public WebMvcConfig(AuthMemberIdArgumentResolver authMemberIdArgumentResolver) {
	// 	this.authMemberIdArgumentResolver = authMemberIdArgumentResolver;
	// }
	//
	// @Override
	// public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
	// 	resolvers.add(authMemberIdArgumentResolver);
	// }
}
