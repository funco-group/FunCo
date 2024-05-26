package com.found_404.funco.feignClient.client;

import com.found_404.funco.feignClient.dto.FollowTradeRequest;
import com.found_404.funco.trade.domain.FutureTrade;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="follow-service")
public interface FollowServiceClient {

    @PostMapping("/api/v1/followTrades")
    void createFollowTrade(@RequestBody List<FollowTradeRequest> tradeList);

    @GetMapping("/api/v1/hello")
    String hello();

    @PostMapping("/api/v1/followTrades/futures")
    void callFollowTradeByFutures(@RequestBody FutureTrade futureTrade);
}
