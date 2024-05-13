package com.found_404.funco.trade.client;

import com.found_404.funco.trade.client.dto.RequestUpdateCash;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="member-service")
public interface MemberServiceClient {

    @PatchMapping(value = "/api/v1/members/{memberId}/cash")
    void updateCash(@PathVariable("memberId") Long memberId, @RequestBody RequestUpdateCash updateCash);
    // 부족 시 bad request

    @GetMapping("/api/v1/hello")
    String hello();
}
