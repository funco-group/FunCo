package com.found_404.funco.feignClient.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.found_404.funco.feignClient.dto.RankMemberInfo;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

	@GetMapping(value = "/api/v1/batch/members")
	List<RankMemberInfo> getAllMemberList();
}
