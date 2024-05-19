package com.found_404.funco.global.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.found_404.funco.asset.dto.request.TotalAssetHistoryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    private static final String TOPIC = "history";

//    @KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
//    public void listen(TotalAssetHistoryRequest totalAssetHistoryRequest) {
//        log.info("kafka consume : {}", totalAssetHistoryRequest);
//
//
//    }

    @KafkaListener(topics = TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        System.out.println("Received raw message: " + message);
        // JSON 문자열을 직접 TotalAssetHistoryRequest 객체로 변환해 봅니다.
        ObjectMapper mapper = new ObjectMapper();
        try {
            TotalAssetHistoryRequest request = mapper.readValue(message, TotalAssetHistoryRequest.class);
            System.out.println("Deserialized message: " + request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}