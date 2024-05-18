package com.found_404.funco.asset.controller;

import java.util.List;

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

	@GetMapping("/history")
	public ResponseEntity<List<? extends AssetHistoryResponse>> getMemberHistory(@AuthMemberId Long memberId,
		AssetHistoryRequest assetHistoryRequest) {

		return ResponseEntity.ok(assetService.getMemberHistory(memberId, assetHistoryRequest.periodType(),
			assetHistoryRequest.assetType(), assetHistoryRequest.tradeType()));
	}
}
