package com.found_404.funco.feignClient.dto.request;

import com.found_404.funco.trade.domain.type.TradeType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TotalAssetHistoryRequest(
        Long memberId,
        TradeType tradeType,
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
