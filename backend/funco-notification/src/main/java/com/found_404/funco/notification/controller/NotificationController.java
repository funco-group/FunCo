package com.found_404.funco.notification.controller;

import com.found_404.funco.global.memberIdHeader.AuthMemberId;
import com.found_404.funco.notification.dto.NotificationDto;
import com.found_404.funco.notification.dto.UnreadCountResponse;
import com.found_404.funco.notification.service.NotificationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RequestMapping("/v1/notifications")
@RequiredArgsConstructor
@RestController
@Slf4j
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@AuthMemberId Long memberId,
                                                HttpServletResponse response) {
        response.setHeader("X-Accel-Buffering", "no"); // NGINX PROXY 에서의 필요설정 불필요한 버퍼링방지

        return ResponseEntity.ok(notificationService.subscribe(memberId));
    }

    @GetMapping()
    public ResponseEntity<List<NotificationDto>> getNotifications(Pageable pageable,
                                                                  @AuthMemberId Long memberId) {
        return ResponseEntity.ok(notificationService.getNotifications(pageable, memberId));
    }

    @PatchMapping("/read")
    public ResponseEntity<?> readNotification(@AuthMemberId Long memberId) {
        notificationService.read(memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unreadCount")
    public ResponseEntity<UnreadCountResponse> getUnreadCount(@RequestParam Long memberId) {
        return ResponseEntity.ok(notificationService.getUnReadCount(memberId));
    }
}
