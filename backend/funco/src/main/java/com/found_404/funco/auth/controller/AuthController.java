package com.found_404.funco.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.auth.dto.response.LoginResponse;
import com.found_404.funco.auth.dto.response.TokenResponse;
import com.found_404.funco.auth.service.AuthService;
import com.found_404.funco.auth.type.OauthServerType;
import com.found_404.funco.member.domain.Member;

import jakarta.servlet.http.HttpServletRequest;
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

	@PostMapping("/reissue")
	public ResponseEntity<TokenResponse> reissueToken(HttpServletRequest request, HttpServletResponse response){
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.reissueToken(request, response));
	}

	@PostMapping("/{provider}/signout")
	public ResponseEntity<Void> logout(HttpServletResponse response, @PathVariable OauthServerType provider,
		@AuthenticationPrincipal Member member){
		authService.logout(response, provider, member);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
