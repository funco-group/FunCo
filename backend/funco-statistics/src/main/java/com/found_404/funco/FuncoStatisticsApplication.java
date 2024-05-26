package com.found_404.funco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FuncoStatisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuncoStatisticsApplication.class, args);
	}

}
