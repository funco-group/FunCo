package com.found_404.funco.global.listener;

import com.found_404.funco.trade.service.FutureService;
import com.found_404.funco.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    private final FutureService futureService;
    private final TradeService tradeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("이전에 등록된 감지될 거래 데이터들을 로딩합니다. ");

        log.info("활성 선물 거래: {} 개 로딩", futureService.loadFuturesTrades());
        log.info("미체결 거래: {} 개 로딩", tradeService.loadOpenTrades());
    }
}