package com.found_404.funco.batch.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.batch.service.BatchAllService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/batch")
public class BatchAllController {

	private final BatchAllService batchAllService;

	// 멤버 별 총 따라오는 투자금
	@GetMapping("/follower/investment")
	public ResponseEntity<Map<Long, Long>> getFollowerInvestmentList() {
		return ResponseEntity.ok(batchAllService.readFollowerInvestmentList());
	}
}
