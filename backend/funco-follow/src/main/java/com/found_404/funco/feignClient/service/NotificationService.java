package com.found_404.funco.feignClient.service;

import com.found_404.funco.feignClient.client.NotificationServiceClient;
import com.found_404.funco.feignClient.dto.NotificationRequest;
import com.found_404.funco.feignClient.dto.NotificationType;
import com.found_404.funco.follow.exception.FollowException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.found_404.funco.follow.exception.FollowErrorCode.NOTIFICATION_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {
	private final NotificationServiceClient notificationServiceClient;
	private final String SERVER_NAME = "[notification-service]";

	@Async
	public void sendNotification(Long memberId, NotificationType notificationType, String message) {
		try {
			notificationServiceClient.createNotification(NotificationRequest.builder()
					.notificationType(notificationType)
					.memberId(memberId)
					.message(message)
					.build());
		} catch (FeignException e) {
			log.error("{} send notification : {}", SERVER_NAME, e.getMessage());
			throw new FollowException(NOTIFICATION_SERVER_ERROR);
		}
	}

}
