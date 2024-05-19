
package com.found_404.funco.feignClient.service;

import com.found_404.funco.feignClient.client.AssetServiceClient;
import com.found_404.funco.feignClient.dto.request.TotalAssetHistoryRequest;
import com.found_404.funco.trade.domain.FutureTrade;
import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.domain.type.TradeType;
import com.found_404.funco.trade.exception.TradeErrorCode;
import com.found_404.funco.trade.exception.TradeException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AssetService {
    private final AssetServiceClient assetServiceClient;
    private final String SERVER_NAME = "[asset-service]";

    @Async
    public void createAssetHistory(Trade trade, Long cash) {
        try {
            this.assetServiceClient.createCoinHistory(TotalAssetHistoryRequest.builder().memberId(trade.getMemberId()).ticker(trade.getTicker()).tradeType(trade.getTradeType()).volume(trade.getVolume()).price(trade.getPrice()).orderCash(trade.getTradeType().equals(TradeType.BUY) ? -trade.getOrderCash() : trade.getOrderCash()).endingCash(cash).build());
        } catch (FeignException e) {
            log.error("{} create Follow Trade By futures error : {}", SERVER_NAME, e.getMessage());
            throw new TradeException(TradeErrorCode.OTHER_SERVER_ERROR);
        }
    }

    @Async
    public void createAssetHistory(FutureTrade futureTrade, Long cash) {
        try {
            this.assetServiceClient.createCoinHistory(TotalAssetHistoryRequest.builder().ticker(futureTrade.getTicker()).tradeType(futureTrade.getTradeType()).price(futureTrade.getPrice()).orderCash(futureTrade.getSettlement()).endingCash(cash).build());
        } catch (FeignException e) {
            log.error("{} create Follow Trade By futures error : {}", SERVER_NAME, e.getMessage());
            throw new TradeException(TradeErrorCode.OTHER_SERVER_ERROR);
        }
    }

}