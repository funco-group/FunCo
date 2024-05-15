package com.found_404.funcomember.feignClient.client;

import com.found_404.funcomember.feignClient.dto.InvestmentsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "follow-service")
public interface FollowServiceClient {

    @GetMapping("/api/investments")
    InvestmentsResponse getInvestments(@RequestParam Long memberId);

    @GetMapping("/api/v1/hello")
    String hello();
}
