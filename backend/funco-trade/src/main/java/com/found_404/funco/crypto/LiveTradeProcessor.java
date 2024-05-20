package com.found_404.funco.crypto;

import com.found_404.funco.crypto.cryptoPrice.LoadTrade;
import com.found_404.funco.trade.domain.type.TradeType;
import com.found_404.funco.trade.service.LiquidateService;
import com.found_404.funco.trade.service.OpenTradeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import static com.found_404.funco.trade.domain.type.TradeType.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class LiveTradeProcessor {
    private final HashMap<String, PriorityBlockingQueue<ProcessingTrade>> buyTrades = new HashMap<>();
    private final HashMap<String, PriorityBlockingQueue<ProcessingTrade>> sellTrades = new HashMap<>();

    private final OpenTradeService openTradeService;
    private final LiquidateService liquidateService;
    private final int BLOCKING_QUEUE_INITIAL_CAPACITY = 11;

    @AllArgsConstructor
    private static class ProcessingTrade {
        Long id;
        Double price;
        TradeType tradeType;
    }

    public void loadTradeData(List<String> markets) {

        for (String market : markets) {
            buyTrades.putIfAbsent(market, new PriorityBlockingQueue<>(BLOCKING_QUEUE_INITIAL_CAPACITY, (t1, t2) -> Double.compare(t2.price, t1.price))); // 최소힙
            sellTrades.putIfAbsent(market, new PriorityBlockingQueue<>(BLOCKING_QUEUE_INITIAL_CAPACITY, (t1, t2) -> Double.compare(t1.price, t2.price))); // 최대힙
        }
        log.info("이전에 등록된 감지될 거래 데이터들을 로딩합니다. ");

        List<LoadTrade> loadTrades = openTradeService.getLoadTrades();
        log.info("미체결 거래: {} 개 로딩", loadTrades.size());
        loadTrades.forEach(loadTrade -> addTrade(loadTrade.ticker(), loadTrade.id(), loadTrade.tradeType(), loadTrade.price()));

        loadTrades = liquidateService.getLoadTrades();
        log.info("활성 선물 거래: {} 개 로딩", loadTrades.size());
        loadTrades.forEach(loadTrade -> addTrade(loadTrade.ticker(), loadTrade.id(), loadTrade.tradeType(), loadTrade.price()));
    }

    public void addTrade(String ticker, Long id, TradeType tradeType, Double price) {
        if (tradeType.equals(BUY) || tradeType.equals(LONG)) {
            log.info("{} 가 {} 이하면 청산", ticker, price);
            buyTrades.get(ticker).add(new ProcessingTrade(id, price, tradeType));
        } else {
            sellTrades.get(ticker).add(new ProcessingTrade(id, price, tradeType));
        }
    }

    @Async
    public void processTrade(String code, Double tradePrice) {
        PriorityBlockingQueue<ProcessingTrade> buyQueue = buyTrades.get(code);
        while (!buyQueue.isEmpty() && buyQueue.peek().price >= tradePrice) {
            ProcessingTrade trade = buyQueue.poll();
            if (trade.tradeType.equals(BUY)) {
                log.info("현물 {} price:{} ,체결", code, tradePrice);
                openTradeService.processTrade(trade.id, tradePrice);
            } else {
                log.info("선물 {} price:{} ,체결", code, tradePrice);
                liquidateService.liquidateFutures(trade.id);
            }
        }

        PriorityBlockingQueue<ProcessingTrade> sellQueue = sellTrades.get(code);
        while (!sellQueue.isEmpty() && sellQueue.peek().price <= tradePrice) {
            ProcessingTrade trade = sellQueue.poll();
            if (trade.tradeType.equals(SELL)) {
                log.info("현물 {} price:{} ,체결", code, tradePrice);
                openTradeService.processTrade(trade.id, tradePrice);
            } else {
                log.info("선물 {} price:{} ,체결", code, tradePrice);
                liquidateService.liquidateFutures(trade.id);
            }
        }

        // 거래 처리
        // if (!concludingTradeIds.isEmpty()) {
        //     log.info("현물 {} price:{} ,체결: {}개", code, tradePrice, concludingTradeIds.size());
        //
        //     //int sizeSum = buyTrades.get(code).size() + sellTrades.get(code).size();
        //     openTradeService.processTrade(concludingTradeIds, tradePrice);
        // }
        //
        // if (!liquidatedFuturesIds.isEmpty()) {
        //     log.info("선물 {} price:{} ,체결: {}개", code, tradePrice, liquidatedFuturesIds.size());
        //
        //     //int sizeSum = buyTrades.get(code).size() + sellTrades.get(code).size();
        //     liquidateService.liquidateFutures(liquidatedFuturesIds);
        // }
    }

}
