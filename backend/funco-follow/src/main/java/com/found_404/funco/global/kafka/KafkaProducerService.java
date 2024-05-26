package com.found_404.funco.global.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {

    private static final String HISTORY = "history";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(HISTORY, message);
    }

    public void sendMessageWithKey(String key, String message) {
        kafkaTemplate.send(HISTORY, key, message);
    }

    public void sendHistory(Long memberId, Object object) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(HISTORY, String.valueOf(memberId), object);
        log.info("kafka [history] send {}에게 {}", memberId, object.toString());
    }
}