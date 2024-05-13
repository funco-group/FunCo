package com.found_404.funco.trade.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class FeignClientConfig {
	@Bean
	public OkHttpClient client() {
		return new OkHttpClient();
	}
}
