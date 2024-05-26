package com.found_404.funco.crypto.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.crypto.cryptoPrice.CryptoPrice;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crypto")
public class CryptoController {
	// 시세 조회 용
	private final CryptoPrice cryptoPrice;

	@GetMapping()
	public ResponseEntity<Map<String, Double>> getCryptoPrice(@RequestParam List<String> tickers) {
		return ResponseEntity.ok(cryptoPrice.getTickerPriceMap(tickers));
	}

}
