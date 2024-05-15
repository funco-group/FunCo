package com.found_404.funco.notification.dto.request;

import com.found_404.funco.notification.domain.type.NotificationType;

public record NotificationRequest(
        Long memberId,
        NotificationType notificationType,
        String message
) {
}
