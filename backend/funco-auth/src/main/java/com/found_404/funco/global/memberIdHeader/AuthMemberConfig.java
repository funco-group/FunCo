package com.found_404.funco.global.memberIdHeader;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.found_404.funco.global.util.OauthServerTypeConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class AuthMemberConfig implements WebMvcConfigurer {
	private final AuthMemberIdArgumentResolver authMemberIdArgumentResolver;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new OauthServerTypeConverter());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authMemberIdArgumentResolver);
	}
}
