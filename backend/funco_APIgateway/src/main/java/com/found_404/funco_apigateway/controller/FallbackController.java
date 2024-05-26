package com.found_404.funco_apigateway.controller;

import com.found_404.funco_apigateway.controller.dto.ErrorCode;
import com.found_404.funco_apigateway.controller.dto.FallbackResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public Mono<FallbackResponse> fallback() {
        return Mono.just(
                new FallbackResponse(
                        ErrorCode.FALLBACK.getCode(),
                        ErrorCode.FALLBACK.getMessage()));
    }

}
