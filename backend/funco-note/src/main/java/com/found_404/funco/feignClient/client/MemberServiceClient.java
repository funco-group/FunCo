package com.found_404.funco.feignClient.client;

import com.found_404.funco.feignClient.dto.SimpleMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="member-service")
public interface MemberServiceClient {

    @GetMapping("/api/v1/members")
    List<SimpleMember> getMembers(@RequestParam List<Long> ids);

    @GetMapping("/api/v1/hello")
    String hello();
}
