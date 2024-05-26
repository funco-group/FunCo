package com.found_404.funcomember.feignClient.dto.request;

import com.found_404.funcomember.feignClient.dto.AssetType;
import com.found_404.funcomember.feignClient.dto.AssetTradeType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TotalAssetHistoryRequest(
        Long memberId,
        AssetType assetType,
        AssetTradeType assetTradeType,
        Double volume,
        Double price,
        Long commission,
        Long settlement,
        Long beginningCash,
        Long endingCash,
        Long orderCash,
        String ticker,
        String portfolioName,
        Long investment,
        String followName,
        LocalDateTime followDate,
        Double followReturnRate
) {
}
