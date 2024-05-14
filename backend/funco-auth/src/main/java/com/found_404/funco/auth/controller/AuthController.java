package com.found_404.funco.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.auth.dto.response.LoginResponse;
import com.found_404.funco.auth.service.AuthService;
import com.found_404.funco.auth.type.OauthServerType;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/{provider}/signin")
	public ResponseEntity<LoginResponse> login(HttpServletResponse response, @PathVariable OauthServerType provider,
		@RequestParam String code) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(response, provider, code));
	}

	// 토큰 재발급 API 구현 필요
	// accessToken만 리턴해주면 된다.
	// accessToken을 뜯어서 expired 여부를 체크한 다음, expired 되었다면 클라이언트에 재발급 해야한다고 요청 보낼 필요 있음
	// 스프링 시큐리티에서 가능할 듯
	// @PostMapping("/reissue")
	// public ResponseEntity<TokenResponse> reissueToken(HttpServletRequest request, HttpServletResponse response) {
	// 	return ResponseEntity.status(HttpStatus.CREATED).body(authService.reissueToken(request, response));
	// }

}
