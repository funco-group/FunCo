package com.found_404.funcomember.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateCash(
        @NotNull
        Long cash
) {
}
