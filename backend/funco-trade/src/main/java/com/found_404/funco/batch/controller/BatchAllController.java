package com.found_404.funco.batch.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.batch.service.BatchAllService;

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
}
