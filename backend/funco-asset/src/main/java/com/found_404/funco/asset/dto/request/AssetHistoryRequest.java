package com.found_404.funco.asset.dto.request;

import com.found_404.funco.asset.domain.type.AssetTradeType;
import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.asset.domain.type.PeriodType;
import jakarta.validation.constraints.NotNull;

public record AssetHistoryRequest(
		@NotNull
		PeriodType period,
		@NotNull
		AssetType asset,
		@NotNull
		AssetTradeType trade
) {
}
