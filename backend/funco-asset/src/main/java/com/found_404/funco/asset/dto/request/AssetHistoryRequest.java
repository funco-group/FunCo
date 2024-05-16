package com.found_404.funco.asset.dto.request;

import com.found_404.funco.asset.domain.type.TradeType;
import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.asset.domain.type.PeriodType;

public record AssetHistoryRequest(
	PeriodType periodType,
	AssetType assetType,
	TradeType tradeType
) {
}
