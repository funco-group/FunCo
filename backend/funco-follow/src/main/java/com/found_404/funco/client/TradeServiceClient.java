package com.found_404.funco.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.found_404.funco.client.dto.CoinValuationResponse;

@FeignClient(name="trade-service")
public interface TradeServiceClient {

    // 보유 코인 별 평가금액 리스트 api
    @GetMapping("/coinValuations")
    CoinValuationResponse getCoinValuations(@RequestParam Long memberId);

    @GetMapping("/api/v1/hello")
    String hello();
}
