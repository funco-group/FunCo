package com.found_404.funcomember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class FuncoMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuncoMemberApplication.class, args);
	}

}
