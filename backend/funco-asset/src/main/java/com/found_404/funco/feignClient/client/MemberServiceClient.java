package com.found_404.funco.feignClient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.found_404.funco.asset.dto.response.CashResponse;
import com.found_404.funco.feignClient.dto.MemberInitCashDate;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

	@GetMapping("/api/v1/members/asset/init-cash")
	MemberInitCashDate getInitCashDate(@RequestParam Long memberId);

	@PatchMapping("/api/v1/members/asset/init-cash")
	Void modifyCashAndInitCashDate(@RequestParam Long memberId);

	@GetMapping("/api/v1/members/{memberId}/cash")
	CashResponse getCash(@PathVariable Long memberId);
}
