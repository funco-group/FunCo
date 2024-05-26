package org.found_404.funco_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer // eureka 서버로서 동작
@SpringBootApplication
public class FuncoEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuncoEurekaApplication.class, args);
	}

}
