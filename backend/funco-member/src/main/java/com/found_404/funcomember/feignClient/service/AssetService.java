
package com.found_404.funcomember.feignClient.service;

import com.found_404.funcomember.feignClient.dto.AssetType;
import com.found_404.funcomember.feignClient.dto.AssetTradeType;
import com.found_404.funcomember.feignClient.dto.request.TotalAssetHistoryRequest;
import com.found_404.funcomember.global.kafka.KafkaProducerService;
import com.found_404.funcomember.portfolio.domain.Subscribe;
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
    public void createPortfolioAssetHistory(Subscribe subscribe, AssetTradeType assetTradeType, Long cash) {
        TotalAssetHistoryRequest totalAssetHistory = TotalAssetHistoryRequest.builder()
                .assetType(AssetType.PORTFOLIO)
                .assetTradeType(assetTradeType)
                .price(Double.valueOf(subscribe.getOrderCash()))
                .memberId(assetTradeType.equals(AssetTradeType.SELL_PORTFOLIO) ? subscribe.getToMember().getId() : subscribe.getFromMember().getId())
                .portfolioName(subscribe.getToMember().getNickname())
                .orderCash(assetTradeType.equals(AssetTradeType.SELL_PORTFOLIO) ? subscribe.getOrderCash() : -subscribe.getOrderCash())
                .endingCash(cash)
                .build();

        kafkaProducerService.sendHistory(totalAssetHistory.memberId(), totalAssetHistory);
//        try {
//            this.assetServiceClient.createCoinHistory(TotalAssetHistoryRequest.builder()
//                    .assetType(AssetType.PORTFOLIO)
//                    .assetTradeType(assetTradeType)
//                    .price(Double.valueOf(subscribe.getOrderCash()))
//                    .memberId(assetTradeType.equals(AssetTradeType.SELL_PORTFOLIO) ? subscribe.getToMember().getId() : subscribe.getFromMember().getId())
//                    .portfolioName(subscribe.getToMember().getNickname())
//                    .orderCash(assetTradeType.equals(AssetTradeType.SELL_PORTFOLIO) ? subscribe.getOrderCash() : -subscribe.getOrderCash())
//                    .endingCash(cash)
//                    .build());
//
//        } catch (FeignException e) {
//            log.error("{} createAssetHistory error : {}", SERVER_NAME, e.getMessage());
//            throw new MemberException(OTHER_SERVER_ERROR);
//        }
    }

}
