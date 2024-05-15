package com.found_404.funco.feignClient.dto;

import lombok.Builder;

@Builder
public record NotificationRequest(
        Long memberId,
        NotificationType notificationType,
        String message
) {
}
