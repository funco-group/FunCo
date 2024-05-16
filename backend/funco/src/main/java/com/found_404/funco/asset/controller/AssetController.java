package com.found_404.funco.asset.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
import com.found_404.funco.asset.dto.response.CashResponse;
import com.found_404.funco.asset.dto.response.CoinHistoryResponse;
import com.found_404.funco.asset.dto.response.CryptoResponse;
import com.found_404.funco.asset.dto.response.HistoryResponse;
import com.found_404.funco.asset.dto.response.TotalAssetResponse;
import com.found_404.funco.asset.service.AssetService;
import com.found_404.funco.global.util.AuthMemberId;
import com.found_404.funco.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AssetController {

	private final AssetService assetService;

	/*
	@GetMapping("/v1/asset")
	public ResponseEntity<TotalAssetResponse> getMemberTotalAsset(@AuthMemberId Long memberId) {
		return ResponseEntity.ok(assetService.getMemberTotalAsset(memberId));
	}*/

	@GetMapping("/v2/asset")
	public ResponseEntity<TotalAssetResponse> getMemberTotalAsset(@AuthMemberId Long memberId) {
		return ResponseEntity.ok(assetService.getMemberTotalAsset(memberId));
	}

	// 남의 보유자산 확인하기
	@GetMapping("/v1/asset/{memberId}")
	public ResponseEntity<TotalAssetResponse> getOtherMemberTotalAsset(@PathVariable Long memberId) {
		return ResponseEntity.ok(assetService.getMemberTotalAsset(memberId));
	}

	@GetMapping("/v1/asset/cash")
	public ResponseEntity<CashResponse> getMemberCash(@AuthenticationPrincipal Member member) {
		return ResponseEntity.ok(assetService.getMemberCash(member));
	}

	@GetMapping("/v1/asset/crypto/{ticker}")
	public ResponseEntity<CryptoResponse> getCrypto(@AuthenticationPrincipal Member member,
		@PathVariable String ticker) {
		return ResponseEntity.ok(assetService.getCrypto(member, ticker));
	}

	@GetMapping("/v1/asset/history")
	public ResponseEntity<List<HistoryResponse>> getMemberHistory(@AuthenticationPrincipal Member member) {

		return ResponseEntity.ok(assetService.getMemberHistory(member));
	}

	@PatchMapping("/v1/asset/init-cash")
	public ResponseEntity<Void> initializeMemberCash(@AuthenticationPrincipal Member member){
		assetService.initializeMemberCash(member);
		return ResponseEntity.ok().build();
	}


	@GetMapping("/v2/asset/history")
	public ResponseEntity<List<? extends AssetHistoryResponse>> getMemberHistoryV2(@AuthMemberId Long memberId, @RequestParam("period") String period,
		@RequestParam("asset") String asset, @RequestParam("trade") String trade) {

		return ResponseEntity.ok(assetService.getMemberHistoryV2(memberId, period, asset, trade));
	}
}
