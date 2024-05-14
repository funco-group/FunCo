package com.found_404.funcomember.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funcomember.member.dto.RequestIntroduction;
import com.found_404.funcomember.member.dto.RequestNickName;
import com.found_404.funcomember.member.dto.request.OAuthMemberRequest;
import com.found_404.funcomember.member.dto.request.UpdateCash;
import com.found_404.funcomember.member.dto.response.CashResponse;
import com.found_404.funcomember.member.dto.response.OAuthMemberResponse;
import com.found_404.funcomember.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {
	private final MemberService memberService;

	// // 유저 페이지 조회
	// @GetMapping("/{memberId}")
	// public ResponseEntity<UserInfoResponse> getMember(
	// 	@AuthMemberId Long loginMemberId,
	// 	@PathVariable Long memberId
	// ) {
	// 	return ResponseEntity.ok(memberService.readMember(loginMemberId, memberId));
	// }
	//
	// // 마이 페이지 조회
	// @GetMapping("/mypage")
	// public ResponseEntity<MyInfoResponse> getMember(@AuthMemberId Long memberId) {
	// 	return ResponseEntity.ok(memberService.readMember(memberId));
	// }

	// 닉네임 변경
	@PatchMapping("/nickname")
	public ResponseEntity<?> updateNickname(Long loginMemberId,
		@RequestBody @Valid RequestNickName requestNickName) {
		memberService.updateNickname(loginMemberId, requestNickName.nickname());
		return ResponseEntity.ok().build();
	}

	// 소개 수정
	@PatchMapping("/introduction")
	public ResponseEntity<?> updateNickname(Long loginMemberId,
		@RequestBody @Valid RequestIntroduction requestIntroduction) {
		memberService.updateIntroduce(loginMemberId, requestIntroduction.introduction());
		return ResponseEntity.ok().build();
	}

	// 회원 탈퇴
	@PatchMapping("/withdraw")
	public ResponseEntity<?> withdraw(Long loginMemberId) {
		memberService.withdraw(loginMemberId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{memberId}/cash")
	public ResponseEntity<?> updateCash(@PathVariable Long memberId, @RequestBody UpdateCash updateCash) {
		memberService.updateCash(memberId, updateCash.cash());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/cash")
	public ResponseEntity<CashResponse> getCash(Long memberId) {
		return ResponseEntity.ok(memberService.getCash(memberId));
	}

	@GetMapping("/{memberId}/cash")
	public ResponseEntity<CashResponse> getMemberCash(@PathVariable Long memberId) {
		return ResponseEntity.ok(memberService.getCash(memberId));
	}

	@GetMapping("/auth/{provider}/{oauthId}")
	public ResponseEntity<OAuthMemberResponse> getAuthMember(@PathVariable String provider,
		@PathVariable String oauthId) {
		return ResponseEntity.ok(memberService.readAuthMember(provider, oauthId));
	}

	@PostMapping
	public ResponseEntity<OAuthMemberResponse> addAuthMember(@RequestBody OAuthMemberRequest OAuthMemberRequest) {
		return ResponseEntity.ok(memberService.createAuthMember(OAuthMemberRequest));
	}
}
