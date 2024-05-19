package com.found_404.funco.asset.dto;

import java.time.LocalDateTime;

import com.found_404.funco.asset.domain.type.TradeType;
import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record CoinHistory(
	LocalDateTime date,
	String ticker,
	TradeType tradeType,
	Double volume,
	Double price,
	Long orderCash,
	Long endingCash
) implements AssetHistoryResponse {


	@QueryProjection
	public CoinHistory(LocalDateTime date, String ticker, TradeType tradeType, Double volume, Double price,
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
