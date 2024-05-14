package com.found_404.funco_apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/health")
	public String healthCheck() {
		return "이 메시지가 보인다면 서버는 살아있다.";
	}
}
