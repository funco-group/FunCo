package com.found_404.funco.trade.controller;

import java.util.List;

import com.found_404.funco.trade.dto.RecentTradedCoin;
import com.found_404.funco.trade.dto.response.CoinValuationResponse;
import com.found_404.funco.trade.dto.response.HoldingCoinResponse;
import com.found_404.funco.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class ServerTradeController {
    private final TradeService tradeService;

    // MSA server 용 API
    @GetMapping("/holding/{ticker}")
    public ResponseEntity<HoldingCoinResponse> getHoldingCoin(@RequestParam Long memberId, @PathVariable String ticker) {
        return ResponseEntity.ok(tradeService.getHoldingCoin(memberId, ticker));
    }

    /* 보유한 코인 별 평가금액, 미체결 금액 포함한 총 거래 자산 */
    @GetMapping("/coinValuations")
    public ResponseEntity<CoinValuationResponse> getCoinValuations(@RequestParam Long memberId) {
        return ResponseEntity.ok(tradeService.getCoinValuations(memberId));
    }

    /* 보유한 코인 조회 */
    @GetMapping("/holding/coins")
    public ResponseEntity<List<HoldingCoinResponse>> getHoldingCoinInfos(@RequestParam Long memberId) {
        return ResponseEntity.ok(tradeService.getHoldingCoinInfos(memberId));
    }

    /* 최근 거래한 3개의 코인 조회 */
    @GetMapping("/recent-coins")
    public ResponseEntity<List<RecentTradedCoin>> getRecentTradedCoins(@RequestParam Long memberId) {
        return ResponseEntity.ok(tradeService.getRecentTradedCoins(memberId));
    }
}
