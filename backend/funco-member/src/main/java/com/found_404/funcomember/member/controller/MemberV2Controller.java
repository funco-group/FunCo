package com.found_404.funcomember.member.controller;

import com.found_404.funcomember.global.memberIdHeader.AuthMemberId;
import com.found_404.funcomember.member.dto.response.UserInfoResponse;
import com.found_404.funcomember.member.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v2/members")
public class MemberV2Controller {
    private final MyPageService myPageService;
	/*
	* 	유저 권한이 필요없는 API
	* */

    @GetMapping("/{memberId}")
	public ResponseEntity<UserInfoResponse> getMember(
	 	@AuthMemberId Long loginMemberId, @PathVariable Long memberId) {
	    return ResponseEntity.ok(myPageService.getMyPage(loginMemberId, memberId));
	}

}
