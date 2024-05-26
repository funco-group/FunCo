package com.found_404.funco.follow.dto.response;

import com.found_404.funco.feignClient.dto.SimpleMember;
import com.found_404.funco.follow.dto.CoinInfo;
import com.found_404.funco.follow.dto.FollowingInfo;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record FollowingResponse(
        SimpleMember member,
        Long followId,
        Long investment,
        LocalDateTime followedAt,
        Long cash,
        List<CoinInfo> coins
) {

    public static FollowingResponse getFollowingResponse(FollowingInfo followingInfo, SimpleMember simpleMember) {
        return FollowingResponse.builder()
                .member(simpleMember)
                .followId(followingInfo.followId())
                .investment(followingInfo.investment())
                .followedAt(followingInfo.followedAt())
                .cash(followingInfo.cash())
                .coins(followingInfo.coins())
                .build();
    }
}
