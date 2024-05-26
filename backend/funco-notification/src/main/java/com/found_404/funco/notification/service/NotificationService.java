package com.found_404.funco.notification.service;

import com.found_404.funco.notification.domain.Notification;
import com.found_404.funco.notification.domain.repository.NotificationRepository;
import com.found_404.funco.notification.domain.type.NotificationType;
import com.found_404.funco.notification.dto.NotificationDto;
import com.found_404.funco.notification.dto.SseMessage;
import com.found_404.funco.notification.dto.UnreadCountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SseEmitterService sseEmitterService;

    private final Map<Long, Integer> unReadCounts = new ConcurrentHashMap<>();

    public UnreadCountResponse getUnReadCount(Long memberId) {
        return new UnreadCountResponse(unReadCounts.getOrDefault(memberId, 0));
    }

    public List<NotificationDto> getNotifications(Pageable pageable, Long memberId) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        return notificationRepository.findAllByMemberId(pageRequest, memberId)
                .stream()
                .map(NotificationDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void read(Long memberId) {
        unReadCounts.put(memberId, 0);
        notificationRepository.updateRead(memberId);
    }

    public SseEmitter subscribe(Long memberId) {
        return sseEmitterService.getNewSseEmitter(memberId);
    }

    public void sendNotification(Long memberId, NotificationType notificationType, String message) {
        log.info("{} 에게 {} 알림 {}", memberId, notificationType, message);
        unReadCounts.put(memberId, unReadCounts.getOrDefault(memberId, 0) + 1);

        notificationRepository.save(Notification.builder()
                .memberId(memberId)
                .type(notificationType)
                .message(message)
                .readYn(Boolean.FALSE)
                .build());

        sseEmitterService.sendSseMessage(memberId, SseMessage.builder()
                .unReadCount(unReadCounts.get(memberId))
                .message(message)
                .notificationDate(LocalDateTime.now().toString())
                .build());
    }

}

