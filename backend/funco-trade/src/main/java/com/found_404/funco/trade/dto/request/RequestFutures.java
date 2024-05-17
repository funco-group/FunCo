package com.found_404.funco.trade.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RequestFutures(
        @NotBlank
        String ticker
) {
}
