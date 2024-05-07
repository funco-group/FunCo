package com.found_404.funco.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

@Configuration
public class HttpClientConfig {

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
