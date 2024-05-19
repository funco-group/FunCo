package com.found_404.funco.asset.dto.request;

import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.asset.domain.type.TradeType;

import java.time.LocalDateTime;

public record TotalAssetHistoryRequest(
        Long memberId, AssetType assetType, TradeType tradeType, Double volume, Double price,
        Long commission, Long settlement, Long beginningCash, Long endingCash, Long orderCash, String ticker,
        String portfolioName, Long investment, String followName, LocalDateTime followDate, Double followReturnRate
) {
}
