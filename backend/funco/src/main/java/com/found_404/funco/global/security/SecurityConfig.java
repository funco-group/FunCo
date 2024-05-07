package com.found_404.funco.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.found_404.funco.global.security.filter.JwtAuthenticationFilter;
import com.found_404.funco.global.security.handler.CustomAuthenticationEntryPoint;
import com.found_404.funco.global.security.service.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration

public class SecurityConfig {

	private final TokenService tokenService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws
		Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
			.cors(AbstractHttpConfigurer::disable)
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth.requestMatchers(CorsUtils::isPreFlightRequest).permitAll())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(new MvcRequestMatcher(introspector, "/v1/auth/*/signin")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/v1/rank/**")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/v1/hello")).permitAll()
				.requestMatchers(new MvcRequestMatcher(introspector, "/h2-console/**")).permitAll()
				// .requestMatchers("/**")
				// .permitAll()
				.anyRequest()
				.authenticated())
		;

		http.addFilterBefore(new JwtAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling(e -> e.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

		return http.build();
	}
}
