package com.found_404.funco.trade.dto.request;

import jakarta.validation.constraints.*;

public record RequestBuyFutures(
        @NotBlank
        String ticker,
        @Positive
        @NotNull
        Long orderCash,

        @Max(200)
        @Min(1)
        Integer leverage
) {

}
