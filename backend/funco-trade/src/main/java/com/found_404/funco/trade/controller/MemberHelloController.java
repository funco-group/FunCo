package com.found_404.funco.trade.controller;

import com.found_404.funco.client.MemberServiceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberHelloController {
    private final MemberServiceClient memberServiceClient;

    @GetMapping("/hello")
    public String hello() {
        try {
            return memberServiceClient.hello();
        } catch (FeignException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return "error";
    }
}
