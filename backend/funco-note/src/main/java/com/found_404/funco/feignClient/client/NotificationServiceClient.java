package com.found_404.funco.feignClient.client;

import com.found_404.funco.feignClient.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="notification-service")
public interface NotificationServiceClient {

    @PostMapping("/api/v1/notifications")
    void createNotification(@RequestBody NotificationRequest notificationRequest);

    @GetMapping("/api/v1/hello")
    String hello();
}
