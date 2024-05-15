package com.found_404.funco.feignClient.dto;

import lombok.Builder;

@Builder
public record SimpleMember(
        Long id,
        String nickname,
        String profileUrl) {
}
