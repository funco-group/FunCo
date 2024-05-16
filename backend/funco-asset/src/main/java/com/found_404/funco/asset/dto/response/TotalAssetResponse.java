package com.found_404.funco.asset.dto.response;

import java.util.List;

import com.found_404.funco.asset.dto.ActiveFutureInfo;
import com.found_404.funco.asset.dto.HoldingCoinInfo;

import lombok.Builder;

@Builder
public record TotalAssetResponse(
        Long cash,
        Long followingInvestment,
        List<HoldingCoinInfo> holdingCoinInfos,
        List<ActiveFutureInfo> activeFutureInfos
    ) {
}
