package com.found_404.funco.asset.dto;

import com.found_404.funco.asset.domain.type.TradeType;
import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FuturesHistory(
	LocalDateTime date,
	String ticker,
	TradeType tradeType,
	Double price,
	Long orderCash,
	Long endingCash
) implements AssetHistoryResponse {

	@QueryProjection
	public FuturesHistory(LocalDateTime date, String ticker, TradeType tradeType, Double price,
						  Long orderCash, Long endingCash) {
		this.date = date;
		this.ticker = ticker;
		this.tradeType = tradeType;
		this.price = price;
		this.orderCash = orderCash;
		this.endingCash = endingCash;
	}
}
