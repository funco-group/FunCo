package com.found_404.funco.feignClient.client;

import com.found_404.funco.feignClient.dto.SimpleMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.found_404.funco.feignClient.dto.CashResponse;
import com.found_404.funco.feignClient.dto.UpdateCash;

import java.util.List;

@FeignClient(name="member-service")
public interface MemberServiceClient {

    @PatchMapping(value = "/api/v1/members/{memberId}/cash")
    void updateCash(@PathVariable("memberId") Long memberId, @RequestBody UpdateCash updateCash);

    @GetMapping("/{memberId}/cash")
    CashResponse getMemberCash(@PathVariable Long memberId);

    @GetMapping()
    List<SimpleMember> getMembers(@RequestParam List<Long> ids);

    @GetMapping("/api/v1/hello")
    String hello();
}
