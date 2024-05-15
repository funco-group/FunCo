package com.found_404.funco.feignClient.client;

import com.found_404.funco.feignClient.dto.HoldingCoinResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.found_404.funco.feignClient.dto.CoinValuationResponse;

import java.util.List;
import java.util.Map;

@FeignClient(name="trade-service")
public interface TradeServiceClient {

    // 보유 코인 별 평가금액 리스트 api
    @GetMapping("/api/v1/trade/coinValuations")
    CoinValuationResponse getCoinValuations(@RequestParam Long memberId);

    @GetMapping("api/v1/crypto")
    Map<String, Long> getCryptoPrice(@RequestParam List<String> tickers);

    @GetMapping("/holding/{ticker}")
    HoldingCoinResponse getHoldingCoin(@RequestParam Long memberId, @PathVariable String ticker);

    @GetMapping("/api/v1/hello")
    String hello();
}
