package com.found_404.funco.follow.controller;

import com.found_404.funco.follow.dto.Trade;
import com.found_404.funco.follow.service.FollowTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/v1/followTrades")
@RequiredArgsConstructor
@RestController
public class FollowTradeController {

    private final FollowTradeService followTradeService;

    @PostMapping()
    public ResponseEntity<?> createFollowTrade(@RequestBody List<Trade> tradeList) {
        followTradeService.followTrade(tradeList);
        return ResponseEntity.ok().build();
    }
}
