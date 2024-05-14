package com.found_404.funco.portfolio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.global.util.AuthMemberId;
import com.found_404.funco.portfolio.dto.request.PortfolioStatusRequest;
import com.found_404.funco.portfolio.dto.request.SubscribeRequest;
import com.found_404.funco.portfolio.service.PortfolioService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/v1/members/portfolio")
@RequiredArgsConstructor
@RestController
public class PortfolioController {
	private final PortfolioService portfolioService;

	@PatchMapping
	public ResponseEntity<Void> modifyPortfolioStatus(@AuthMemberId Long memberId,
		@RequestBody PortfolioStatusRequest portfolioStatusRequest
	) {
		portfolioService.updatePortfolioStatus(memberId, portfolioStatusRequest);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/subscribe")
	public ResponseEntity<Void> addPortfolio(
		@AuthMemberId Long memberId,
		@RequestBody SubscribeRequest subscribeRequest) {
		portfolioService.createPortfolio(memberId, subscribeRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
