package com.found_404.funco.asset.controller;

import java.util.List;

import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.asset.domain.type.TradeType;
import com.found_404.funco.asset.dto.request.TotalAssetHistoryRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.found_404.funco.asset.dto.request.AssetHistoryRequest;
import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
import com.found_404.funco.asset.dto.response.TotalAssetResponse;
import com.found_404.funco.asset.service.AssetService;
import com.found_404.funco.global.memberIdHeader.AuthMemberId;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/asset")
@Slf4j
public class AssetController {

	private final AssetService assetService;

	// 보유 자산 요약
	@GetMapping()
	public ResponseEntity<TotalAssetResponse> getMemberTotalAsset(@AuthMemberId Long memberId) {
		return ResponseEntity.ok(assetService.getMemberTotalAsset(memberId));
	}

	// 남의 보유자산 요약 확인하기
	@GetMapping("/{memberId}")
	public ResponseEntity<TotalAssetResponse> getOtherMemberTotalAsset(@PathVariable Long memberId) {
		return ResponseEntity.ok(assetService.getMemberTotalAsset(memberId));
	}


	@PatchMapping("/init-cash")
	public ResponseEntity<Void> initializeMemberCash(@AuthMemberId Long memberId){
		assetService.initializeMemberCash(memberId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// ----------------- 통합 자산 내역 -----------------------------
	@GetMapping("/history")
	public ResponseEntity<List<? extends AssetHistoryResponse>> getMemberHistory(@AuthMemberId Long memberId,
		@Valid AssetHistoryRequest assetHistoryRequest) {

		return ResponseEntity.ok(assetService.getMemberHistory(memberId, assetHistoryRequest.period(),
			assetHistoryRequest.asset(), assetHistoryRequest.trade()));
	}

	// post 코인
	@PostMapping("/histories/coin")
	public ResponseEntity<?> createCoinHistory(@RequestBody TotalAssetHistoryRequest totalAssetHistoryRequest) {
		assetService.saveDataToAssetHistory(totalAssetHistoryRequest, AssetType.COIN);
		return ResponseEntity.ok().build();
	}

	// post 선물
	@PostMapping("/histories/futures")
	public ResponseEntity<?> createFuturesHistory(@RequestBody TotalAssetHistoryRequest totalAssetHistoryRequest) {
		assetService.saveDataToAssetHistory(totalAssetHistoryRequest, AssetType.FUTURES);
		return ResponseEntity.ok().build();
	}

	// post 팔로우
	@PostMapping("/histories/follow")
	public ResponseEntity<?> createFollowHistory(@RequestBody TotalAssetHistoryRequest totalAssetHistoryRequest) {
		assetService.saveDataToAssetHistory(totalAssetHistoryRequest, AssetType.FOLLOW);
		return ResponseEntity.ok().build();
	}

	// post 포폴
	@PostMapping("/histories/portfolio")
	public ResponseEntity<?> createPortfolioHistory(@RequestBody TotalAssetHistoryRequest totalAssetHistoryRequest) {
		totalAssetHistoryRequest.setTradeType(totalAssetHistoryRequest.getOrderCash() > 0 ? TradeType.SELL_PORTFOLIO : TradeType.PURCHASE_PORTFOLIO);

		assetService.saveDataToAssetHistory(totalAssetHistoryRequest, AssetType.PORTFOLIO);
		return ResponseEntity.ok().build();
	}

}
