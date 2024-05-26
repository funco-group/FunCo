package com.found_404.funcomember.member.dto.response;

import lombok.Builder;

@Builder
public record SimpleMember(
        Long id,
        String nickname,
        String profileUrl) {
}
