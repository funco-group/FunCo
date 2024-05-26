package com.found_404.funco.follow.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FollowerInfo(
        Long followId,
        LocalDateTime followedAt,
        Long investment,
        Long settlement,
        Double returnRate,
        Long commission,
        LocalDateTime settleDate

) {

    @QueryProjection
    public FollowerInfo(Long followId, LocalDateTime followedAt, Long investment, Long settlement,
                        Double returnRate, Long commission, LocalDateTime settleDate) {
        this.followId = followId;
        this.followedAt = followedAt;
        this.investment = investment;
        this.settlement = settlement;
        this.returnRate = returnRate;
        this.commission = commission;
        this.settleDate = settleDate;
    }
}