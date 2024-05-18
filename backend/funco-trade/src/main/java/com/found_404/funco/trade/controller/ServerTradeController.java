package com.found_404.funco.trade.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.feignClient.dto.ActiveFutureInfo;
import com.found_404.funco.feignClient.dto.HoldingCoinInfo;
import com.found_404.funco.trade.dto.RecentTradedCoin;
import com.found_404.funco.trade.dto.response.CoinValuationResponse;
import com.found_404.funco.trade.dto.response.HoldingCoinResponse;
import com.found_404.funco.trade.service.TradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class ServerTradeController {
	private final TradeService tradeService;

	// MSA server 용 API
	@GetMapping("/holding/{ticker}")
	public ResponseEntity<HoldingCoinResponse> getHoldingCoin(@RequestParam Long memberId,
		@PathVariable String ticker) {
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

	@DeleteMapping("/coins")
	public ResponseEntity<Void> removeCoins(@RequestParam Long memberId) {
		tradeService.removeCoins(memberId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/asset/holding")
	public ResponseEntity<List<HoldingCoinInfo>> getAssetHoldingCoin(@RequestParam Long memberId) {
		return ResponseEntity.ok(tradeService.getAssetHoldingCoin(memberId));
	}

	@GetMapping("/asset/future")
	public ResponseEntity<List<ActiveFutureInfo>> getAssetFuture(@RequestParam Long memberId) {
		return ResponseEntity.ok(tradeService.getAssetFuture(memberId));
	}
}
