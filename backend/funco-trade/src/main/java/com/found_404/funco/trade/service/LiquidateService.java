package com.found_404.funco.trade.service;

import com.found_404.funco.crypto.cryptoPrice.LoadTrade;
import com.found_404.funco.feignClient.service.NotificationService;
import com.found_404.funco.trade.domain.ActiveFuture;
import com.found_404.funco.trade.domain.FutureTrade;
import com.found_404.funco.trade.domain.repository.ActiveFutureRepository;
import com.found_404.funco.trade.domain.repository.FutureTradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.found_404.funco.feignClient.dto.NotificationType.FUTURES;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquidateService {
    private final ActiveFutureRepository activeFutureRepository;
    private final FutureTradeRepository futureTradeRepository;
    private final NotificationService notificationService;

    // 청산 처리
    @Async
    @Transactional
    public void liquidateFutures(List<Long> ids) {
        List<ActiveFuture> activeFutures = activeFutureRepository.findAllByIdIn(ids);
        activeFutureRepository.deleteAll(activeFutures);

        List<FutureTrade> futureTrades = futureTradeRepository.saveAll(
                activeFutures.stream()
                        .map(FutureTrade::getLiquidatedFutures)
                        .toList());

        // [API async] 알림
        futureTrades.forEach(futureTrade ->
                notificationService.sendNotification(futureTrade.getMemberId(), FUTURES, futureTrade.getTicker() +  " 선물 거래가 강제 청산되었습니다.")
        );
    }

    public List<LoadTrade> getLoadTrades() {
        return activeFutureRepository.findAll()
                .stream().map(LoadTrade::getLoadTrade)
                .toList();
    }
}
