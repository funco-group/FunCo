package com.found_404.funco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FuncoRankApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuncoRankApplication.class, args);
	}

}
