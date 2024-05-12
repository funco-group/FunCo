package com.found_404.funco.trade.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="member-service")
public interface MemberServiceClient {

    @PatchMapping("/{memberId}/cash")
    void updateCash(@PathVariable Long memberId, @RequestBody Long updateCash);
    // 부족 시 bad request
}
