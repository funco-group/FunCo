package com.found_404.funco.trade.service;

import com.found_404.funco.trade.domain.ActiveFuture;
import com.found_404.funco.trade.domain.FutureTrade;
import com.found_404.funco.trade.domain.repository.ActiveFutureRepository;
import com.found_404.funco.trade.domain.repository.FutureTradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquidateService {
    private final ActiveFutureRepository activeFutureRepository;
    private final FutureTradeRepository futureTradeRepository;


    // 청산 처리
    @Async
    public void liquidateFutures(List<Long> ids) {
        List<ActiveFuture> activeFutures = activeFutureRepository.findAllByIdIn(ids);
        activeFutureRepository.deleteAll(activeFutures);

        futureTradeRepository.saveAll(
                activeFutures.stream()
                        .map(FutureTrade::getLiquidatedFutures)
                        .toList());
    }
}
