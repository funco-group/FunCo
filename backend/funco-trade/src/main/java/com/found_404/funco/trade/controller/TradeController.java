package com.found_404.funco.trade.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.trade.dto.OpenTradeDto;
import com.found_404.funco.trade.dto.OtherTradeDto;
import com.found_404.funco.trade.dto.TradeDto;
import com.found_404.funco.trade.dto.request.LimitBuyingRequest;
import com.found_404.funco.trade.dto.request.LimitSellingRequest;
import com.found_404.funco.trade.dto.request.MarketBuyingRequest;
import com.found_404.funco.trade.dto.request.MarketSellingRequest;
import com.found_404.funco.trade.dto.request.TradeRequest;
import com.found_404.funco.trade.dto.response.HoldingCoinsResponse;
import com.found_404.funco.trade.dto.response.MarketTradeResponse;
import com.found_404.funco.trade.service.TradeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/trade")
@RequiredArgsConstructor
public class TradeController {
	private final TradeService tradeService;
	private final String MEMBER_ID_HEADER = "X-Member-ID";

	// 시장가 매수
	@PostMapping("/market-buying")
	public ResponseEntity<MarketTradeResponse> marketBuying(@RequestHeader(MEMBER_ID_HEADER) Long memberId,
		@RequestBody @Valid MarketBuyingRequest request) {

		return ResponseEntity.ok(tradeService.marketBuying(memberId, request.ticker(), request.orderCash()));
	}

	// 시장가 매도
	@PostMapping("/market-selling")
	public ResponseEntity<MarketTradeResponse> marketSelling(@RequestHeader(MEMBER_ID_HEADER) Long memberId,
		@RequestBody @Valid MarketSellingRequest request) {

		return ResponseEntity.ok(tradeService.marketSelling(memberId, request.ticker(), request.volume()));
	}

	// 지정가 매수
	@PostMapping("/limit-buying")
	public ResponseEntity<?> limitBuying(@RequestHeader(MEMBER_ID_HEADER) Long memberId,
		@RequestBody @Valid LimitBuyingRequest request) {

		tradeService.limitBuying(memberId, request.ticker(), request.price(), request.volume());
		return ResponseEntity.ok().build();
	}

	// 지정가 매도
	@PostMapping("/limit-selling")
	public ResponseEntity<?> limitSelling(@RequestHeader(MEMBER_ID_HEADER) Long memberId,
		@RequestBody @Valid LimitSellingRequest request) {

		tradeService.limitSelling(memberId, request.ticker(), request.price(), request.volume());
		return ResponseEntity.ok().build();
	}

	// 보유 중인 코인 조회
	@GetMapping("/holding")
	public ResponseEntity<HoldingCoinsResponse> getHoldingCoin(@RequestHeader(MEMBER_ID_HEADER) Long memberId) {
		return ResponseEntity.ok(tradeService.getHoldingCoins(memberId));
	}

	// 체결 코인 거래 내역, param ticker
	@GetMapping("/orders")
	public ResponseEntity<List<TradeDto>> getOrders(@RequestHeader(MEMBER_ID_HEADER) Long memberId,
		Pageable pageable, TradeRequest tradeRequest) {

		return ResponseEntity.ok(tradeService.getOrders(memberId, tradeRequest.ticker(), pageable));
	}

	// 남의 체결 코인 거래 내역
	@GetMapping("/orders/{memberId}")
	public ResponseEntity<List<OtherTradeDto>> getOtherOrders(@PathVariable Long memberId,
															  Pageable pageable) {

		return ResponseEntity.ok(tradeService.getOtherOrders(memberId, pageable));
	}

	// 미체결 거래 내역 보기
	@GetMapping("/open-orders")
	public ResponseEntity<List<OpenTradeDto>> getOpenOrders(@RequestHeader(MEMBER_ID_HEADER) Long memberId,
		Pageable pageable, TradeRequest tradeRequest) {

		return ResponseEntity.ok(tradeService.getOpenOrders(memberId, tradeRequest.ticker(), pageable));
	}

	// 미체결 거래 취소
	@DeleteMapping("/open-orders/{id}")
	public ResponseEntity<?> getOpenOrders(@RequestHeader(MEMBER_ID_HEADER) Long memberId,
		@PathVariable Long id) {

		tradeService.deleteOpenTrade(memberId, id);
		return ResponseEntity.ok().build();
	}

}
