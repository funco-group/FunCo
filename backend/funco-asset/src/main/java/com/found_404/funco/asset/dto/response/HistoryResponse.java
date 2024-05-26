package com.found_404.funco.asset.dto.response;

import java.time.LocalDateTime;

import com.found_404.funco.asset.domain.type.AssetType;

import lombok.Builder;

@Builder
public record HistoryResponse(

	LocalDateTime date,
	String name,
	AssetType assetType,
	String tradeType,
	Double volume,
	Long orderCash,
	Long price,
	Long commission,
	Long settlement

) {
}
