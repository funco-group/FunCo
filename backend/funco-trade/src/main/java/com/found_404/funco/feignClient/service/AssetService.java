
package com.found_404.funco.feignClient.service;

import com.found_404.funco.feignClient.dto.AssetTradeType;
import com.found_404.funco.feignClient.dto.AssetType;
import com.found_404.funco.feignClient.dto.request.TotalAssetHistoryRequest;
import com.found_404.funco.global.kafka.KafkaProducerService;
import com.found_404.funco.trade.domain.FutureTrade;
import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.domain.type.TradeType;
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
    public void createAssetHistory(Trade trade, Long cash) {
        TotalAssetHistoryRequest totalAssetHistoryRequest = TotalAssetHistoryRequest.builder()
                .memberId(trade.getMemberId())
                .ticker(trade.getTicker())
                .assetType(AssetType.COIN)
                .assetTradeType(AssetTradeType.tradeTypeToAssetTradeType(trade.getTradeType()))
                .volume(trade.getVolume())
                .price(trade.getPrice())
                .orderCash(trade.getTradeType().equals(TradeType.BUY) ? -trade.getOrderCash() : trade.getOrderCash())
                .endingCash(cash)
                .build();

        kafkaProducerService.sendHistory(trade.getMemberId(), totalAssetHistoryRequest);

//        try {
//            this.assetServiceClient.createCoinHistory(totalAssetHistoryRequest);
//        } catch (FeignException e) {
//            log.error("{} create Follow Trade By futures error : {}", SERVER_NAME, e.getMessage());
//            throw new TradeException(TradeErrorCode.OTHER_SERVER_ERROR);
//        }
    }

    @Async
    public void createAssetHistory(FutureTrade futureTrade, Long cash) {
        TotalAssetHistoryRequest totalAssetHistoryRequest = TotalAssetHistoryRequest.builder()
                .memberId(futureTrade.getMemberId())
                .ticker(futureTrade.getTicker())
                .assetTradeType(AssetTradeType.tradeTypeToAssetTradeType(futureTrade.getTradeType()))
                .assetType(AssetType.FUTURES)
                .price(futureTrade.getPrice())
                .orderCash(futureTrade.getSettlement())
                .endingCash(cash)
                .build();

        kafkaProducerService.sendHistory(futureTrade.getMemberId(), totalAssetHistoryRequest);
//
//        try {
//            this.assetServiceClient.createFuturesHistory(totalAssetHistoryRequest);
//        } catch (FeignException e) {
//            log.error("{} create Follow Trade By futures error : {}", SERVER_NAME, e.getMessage());
//            throw new TradeException(TradeErrorCode.OTHER_SERVER_ERROR);
//        }
    }

}
