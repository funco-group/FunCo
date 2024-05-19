package com.found_404.funco.global.kafka;

import com.found_404.funco.asset.dto.request.TotalAssetHistoryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    private static final String TOPIC = "history";

    @KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void listen(TotalAssetHistoryRequest totalAssetHistoryRequest) {
        log.info("kafka consume : {}", totalAssetHistoryRequest);
    }
}