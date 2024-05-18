package com.found_404.funco.follow.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.follow.dto.FollowTradeDto;
import com.found_404.funco.follow.dto.request.FollowerProfitRequest;
import com.found_404.funco.follow.dto.request.FollowingRequest;
import com.found_404.funco.follow.dto.response.FollowAssetResponse;
import com.found_404.funco.follow.dto.response.FollowStatusResponse;
import com.found_404.funco.follow.dto.response.FollowerInfoResponse;
import com.found_404.funco.follow.dto.response.FollowerListResponse;
import com.found_404.funco.follow.dto.response.FollowingListResponse;
import com.found_404.funco.follow.dto.response.InvestmentsResponse;
import com.found_404.funco.follow.service.FollowService;
import com.found_404.funco.global.memberIdHeader.AuthMemberId;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/v1/follows")
@RequiredArgsConstructor
@RestController
public class FollowController {

	private final FollowService followService;

	@PostMapping
	public ResponseEntity<Void> addFollow(@AuthMemberId Long memberId, @RequestBody @Valid FollowingRequest request) {
		followService.createFollow(request, memberId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/{followId}")
	public ResponseEntity<Void> removeFollow(@PathVariable Long followId) {
		followService.deleteFollow(followId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/following")
	public ResponseEntity<FollowingListResponse> getFollowingList(@AuthMemberId Long memberId,
		@RequestParam(required = false) Long lastFollowId) {
		return ResponseEntity.status(HttpStatus.OK).body(followService.readFollowingList(memberId, lastFollowId));
	}

	@GetMapping("/follower")
	public ResponseEntity<FollowerListResponse> getFollowerList(@AuthMemberId Long memberId,
		@RequestParam String settled,
		@RequestParam(required = false) Long lastFollowId) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(followService.readFollowerList(memberId, settled, lastFollowId));
	}

	@GetMapping("/{followId}/trades")
	public ResponseEntity<List<FollowTradeDto>> getFollowTrades(@PathVariable Long followId, Pageable pageable) {

		return ResponseEntity.ok(followService.getFollowTrades(pageable, followId));
	}

	/*
	 * 	MSA SERVER API
	 * */
	@GetMapping("/investments")
	public ResponseEntity<InvestmentsResponse> getInvestments(@RequestParam Long memberId) {
		return ResponseEntity.ok(followService.getInvestments(memberId));
	}

	@GetMapping("/{followingId}/followers")
	public ResponseEntity<List<FollowerInfoResponse>> getFollowerInfos(@PathVariable Long followingId) {
		return ResponseEntity.ok(followService.getFollowerInfos(followingId));
	}

	@PatchMapping("/{followingId}/followers")
	public ResponseEntity<Void> modifyFollower(@PathVariable Long followingId,
		@RequestBody FollowerProfitRequest followerProfitRequest) {
		followService.updateFollower(followingId, followerProfitRequest);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/*
	 * 	MSA SERVER API
	 * */
	@GetMapping("/{loginMemberId}/following/{targetMemberId}")
	public ResponseEntity<FollowStatusResponse> getFollowStatus(@PathVariable Long loginMemberId,
		@PathVariable Long targetMemberId) {
		return ResponseEntity.ok(followService.getFollowStatus(loginMemberId, targetMemberId));
	}

	@GetMapping("/asset")
	public ResponseEntity<FollowAssetResponse> getFollowAsset(@RequestParam Long memberId) {
		return ResponseEntity.ok(followService.getFollowAsset(memberId));
	}

	@PatchMapping("/asset")
	public ResponseEntity<Void> modifyFollowingAndFollower(@RequestParam Long memberId) {
		followService.modifyFollowingAndFollower(memberId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/following/investments")
	public ResponseEntity<InvestmentsResponse> getFollowingInvestment(@RequestParam Long memberId) {
		return ResponseEntity.ok(followService.getFollowingInvestment(memberId));
	}
}
