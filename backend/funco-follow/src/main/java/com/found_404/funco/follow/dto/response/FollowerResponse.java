package com.found_404.funco.follow.dto.response;

import com.found_404.funco.feignClient.dto.SimpleMember;
import com.found_404.funco.follow.domain.Follow;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record FollowerResponse(
        SimpleMember member,
        Long followId,
        LocalDateTime followedAt,
        Long investment,
        Long settlement,
        Double returnRate,
        Long commission,
        LocalDateTime settleDate
) {
    public static FollowerResponse getFollowerResponse(Follow follow, SimpleMember member) {
        return FollowerResponse.builder()
                .member(member)
                .followId(follow.getId())
                .followedAt(follow.getCreatedAt())
                .investment(follow.getInvestment())
                .settlement(follow.getSettlement())
                .returnRate(follow.getReturnRate())
                .commission(follow.getCommission())
                .settleDate(follow.getSettleDate())
                .build();
    }

}
