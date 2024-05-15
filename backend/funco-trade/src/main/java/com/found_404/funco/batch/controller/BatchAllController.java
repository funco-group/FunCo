package com.found_404.funco.batch.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.batch.service.BatchAllService;
import com.found_404.funco.trade.dto.HoldingCoinInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/batch")
@RequiredArgsConstructor
public class BatchAllController {

	private final BatchAllService batchAllService;

	// [rank] 보유 코인 종류들 조회 API
	@GetMapping("/holdings/ticker")
	public ResponseEntity<List<String>> getHoldingCoinList() {
		return ResponseEntity.ok(batchAllService.readHoldingCoinList());
	}

	// [rank] 멤버 별 보유 코인 조회
	@GetMapping("/holdings")
	public ResponseEntity<List<HoldingCoinInfo>> getHoldingCoinInfoList() {
		return ResponseEntity.ok(batchAllService.readHoldingCoinInfoList());
	}

	// [rank] 멤버 별 지정가 거래 코인 주문 금액 조회
	@GetMapping("/api/v1/opentrades")
	public ResponseEntity<Map<Long, Long>> getOpenTradeOrderCashList() {
		return ResponseEntity.ok(batchAllService.readOpenTradeOrderCashList());
	}
}
