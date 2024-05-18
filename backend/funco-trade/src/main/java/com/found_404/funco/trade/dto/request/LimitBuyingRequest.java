package com.found_404.funco.trade.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LimitBuyingRequest(
        @NotBlank
        String ticker,
        @NotNull
        @Positive
        Double price,
        @NotNull
        @Positive
        Double volume
) {
}
