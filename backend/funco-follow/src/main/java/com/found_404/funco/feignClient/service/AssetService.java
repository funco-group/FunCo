
package com.found_404.funco.feignClient.service;

import com.found_404.funco.feignClient.dto.AssetType;
import com.found_404.funco.feignClient.dto.AssetTradeType;
import com.found_404.funco.feignClient.dto.request.TotalAssetHistoryRequest;
import com.found_404.funco.follow.domain.Follow;
import com.found_404.funco.global.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AssetService {
    //private final AssetServiceClient assetServiceClient;
    private final String SERVER_NAME = "[asset-service]";

    private final KafkaProducerService kafkaProducerService;

    @Async
    public void createAssetHistory(Follow follow, AssetTradeType assetTradeType, Long cash) {
        TotalAssetHistoryRequest totalAssetHistoryRequest = TotalAssetHistoryRequest.builder()
                .assetTradeType(assetTradeType)
                .assetType(AssetType.FOLLOW)
                .memberId(assetTradeType.equals(AssetTradeType.FOLLOWING) ? follow.getFollowerMemberId() : follow.getFollowingMemberId())
                .investment(follow.getInvestment())
                .settlement(follow.getSettlement())
                .orderCash(follow.getSettlement())
                .followReturnRate(follow.getReturnRate())
                .commission(follow.getCommission())
                .endingCash(cash)
                .followDate(follow.getCreatedAt())
                .build();

        kafkaProducerService.sendHistory(totalAssetHistoryRequest.memberId(), totalAssetHistoryRequest);
//        try {
//            this.assetServiceClient.createCoinHistory(TotalAssetHistoryRequest.builder()
//                    .assetTradeType(assetTradeType)
//                    .assetType(AssetType.FOLLOW)
//                    .memberId(assetTradeType.equals(AssetTradeType.FOLLOWING) ? follow.getFollowerMemberId() : follow.getFollowingMemberId())
//                    .investment(follow.getInvestment())
//                    .settlement(follow.getSettlement())
//                    .orderCash(follow.getSettlement())
//                    .followReturnRate(follow.getReturnRate())
//                    .commission(follow.getCommission())
//                    .endingCash(cash)
//                    .followDate(follow.getCreatedAt())
//                    .build());
//        } catch (FeignException e) {
//            log.error("{} createAssetHistory error : {}", SERVER_NAME, e.getMessage());
//            throw new FollowException(OTHER_SERVER_ERROR);
//        }
    }

}
