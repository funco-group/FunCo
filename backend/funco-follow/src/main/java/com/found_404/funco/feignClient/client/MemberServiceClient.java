package com.found_404.funco.feignClient.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.found_404.funco.feignClient.dto.CashResponse;
import com.found_404.funco.feignClient.dto.SimpleMember;
import com.found_404.funco.feignClient.dto.UpdateCash;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

	@PatchMapping(value = "/api/v1/members/{memberId}/cash")
	void updateCash(@PathVariable("memberId") Long memberId, @RequestBody UpdateCash updateCash);

	@GetMapping("/api/v1/members/{memberId}/cash")
	CashResponse getMemberCash(@PathVariable Long memberId);

	@GetMapping("/api/v1/members")
	List<SimpleMember> getMembers(@RequestParam List<Long> ids);

	@GetMapping("/api/v1/hello")
	String hello();
}
