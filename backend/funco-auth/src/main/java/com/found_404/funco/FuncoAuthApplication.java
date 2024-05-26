package com.found_404.funco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class FuncoAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuncoAuthApplication.class, args);
	}

}
