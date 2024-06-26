package com.found_404.funcomember.feignClient.client;

import com.found_404.funcomember.feignClient.dto.CoinValuationResponse;
import com.found_404.funcomember.feignClient.dto.HoldingCoinResponse;
import com.found_404.funcomember.feignClient.dto.RecentTradedCoin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "trade-service")
public interface TradeServiceClient {

    // 보유 코인 별 평가금액 리스트 api
    @GetMapping("/api/trade/coinValuations")
    CoinValuationResponse getCoinValuations(@RequestParam Long memberId);

    @GetMapping("/api/crypto")
    Map<String, Double> getCryptoPrice(@RequestParam List<String> tickers);

    @GetMapping("/api/trade/holding/coins")
    List<HoldingCoinResponse> getHoldingCoinInfos(@RequestParam Long memberId);

    @GetMapping("/api/trade/recent-coins")
    List<RecentTradedCoin> getRecentTradedCoins(@RequestParam Long memberId);

    @GetMapping("/api/v1/hello")
    String hello();
}
