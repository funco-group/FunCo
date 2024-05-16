package com.found_404.funco.asset.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.asset.dto.request.AssetHistoryRequest;
import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
import com.found_404.funco.asset.dto.response.TotalAssetResponse;
import com.found_404.funco.asset.service.AssetService;
import com.found_404.funco.global.memberIdHeader.AuthMemberId;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AssetController {

	private final AssetService assetService;

	// 보유 자산 요약
	@GetMapping("/v1/asset")
	public ResponseEntity<TotalAssetResponse> getMemberTotalAsset(@AuthMemberId Long memberId) {
		return ResponseEntity.ok(assetService.getMemberTotalAsset(memberId));
	}

	// 남의 보유자산 요약 확인하기
	@GetMapping("/v1/asset/{memberId}")
	public ResponseEntity<TotalAssetResponse> getOtherMemberTotalAsset(@PathVariable Long memberId) {
		return ResponseEntity.ok(assetService.getMemberTotalAsset(memberId));
	}


	@PatchMapping("/v1/asset/init-cash")
	public ResponseEntity<Void> initializeMemberCash(@AuthMemberId Long memberId){
		assetService.initializeMemberCash(memberId);
		return ResponseEntity.ok().build();
	}


	@GetMapping("/v1/asset/history")
	public ResponseEntity<List<? extends AssetHistoryResponse>> getMemberHistory(@AuthMemberId Long memberId,
		AssetHistoryRequest assetHistoryRequest) {

		return ResponseEntity.ok(assetService.getMemberHistory(memberId, assetHistoryRequest.periodType(),
			assetHistoryRequest.assetType(), assetHistoryRequest.tradeType()));
	}
}
