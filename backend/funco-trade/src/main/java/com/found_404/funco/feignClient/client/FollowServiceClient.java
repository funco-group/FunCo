package com.found_404.funco.feignClient.client;

import com.found_404.funco.feignClient.dto.FollowTradeRequest;
import com.found_404.funco.feignClient.dto.UpdateCash;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="follow-service")
public interface FollowServiceClient {

    @PatchMapping(value = "/api/v1/members/{memberId}/cash")
    void updateCash(@PathVariable("memberId") Long memberId, @RequestBody UpdateCash updateCash);

    @PostMapping()
    void createFollowTrade(@RequestBody List<FollowTradeRequest> tradeList);

    @GetMapping("/api/v1/hello")
    String hello();
}
