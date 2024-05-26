package com.found_404.funco.global.kafka;

import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.asset.dto.request.TotalAssetHistoryRequest;
import com.found_404.funco.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
    private final AssetService assetService;

    private static final String TOPIC = "history";

    @KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void listen(TotalAssetHistoryRequest totalAssetHistoryRequest) {
        log.info("kafka consume : {}", totalAssetHistoryRequest);

        if (AssetType.COIN.equals(totalAssetHistoryRequest.getAssetType())) {
            assetService.saveDataToAssetHistory(totalAssetHistoryRequest, AssetType.COIN);
        } else if (AssetType.FUTURES.equals(totalAssetHistoryRequest.getAssetType())) {
            assetService.saveDataToAssetHistory(totalAssetHistoryRequest, AssetType.FUTURES);
        } else if (AssetType.FOLLOW.equals(totalAssetHistoryRequest.getAssetType())) {
            assetService.saveDataToAssetHistory(totalAssetHistoryRequest, AssetType.FOLLOW);
        } else if (AssetType.PORTFOLIO.equals(totalAssetHistoryRequest.getAssetType())) {
            assetService.saveDataToAssetHistory(totalAssetHistoryRequest, AssetType.PORTFOLIO);
        } else {
            log.info("kafka consume history data AssetType 없음");
        }
    }

}