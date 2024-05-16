package com.found_404.funco.asset.dto.response;

import java.time.LocalDateTime;

import com.found_404.funco.asset.domain.type.TradeType;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record CoinHistoryResponse(
	LocalDateTime date,
	String ticker,
	TradeType tradeType,
	Double volume,
	Long price,
	Long orderCash,
	Long endingCash
) implements AssetHistoryResponse {


	@QueryProjection
	public CoinHistoryResponse(LocalDateTime date, String ticker, TradeType tradeType, Double volume, Long price,
		Long orderCash, Long endingCash) {
		this.date = date;
		this.ticker = ticker;
		this.tradeType = tradeType;
		this.volume = volume;
		this.price = price;
		this.orderCash = orderCash;
		this.endingCash = endingCash;
	}
}
