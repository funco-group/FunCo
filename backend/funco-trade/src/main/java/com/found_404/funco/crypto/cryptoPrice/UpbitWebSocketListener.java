package com.found_404.funco.crypto.cryptoPrice;

import static com.found_404.funco.trade.domain.type.TradeType.*;
import static com.found_404.funco.trade.exception.TradeErrorCode.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.found_404.funco.trade.service.LiquidateService;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.found_404.funco.global.util.HttpClientUtil;
import com.found_404.funco.crypto.cryptoPrice.jsonObject.CryptoJson;
import com.found_404.funco.trade.domain.type.TradeType;
import com.found_404.funco.trade.exception.TradeException;
import com.found_404.funco.trade.service.OpenTradeService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitWebSocketListener extends WebSocketListener {
    private final HttpClientUtil httpClientUtil;
    private final OpenTradeService openTradeService;
    private final LiquidateService liquidateService;

    private final Map<String, Double> cryptoPrices = new HashMap<>();
    private final ConcurrentHashMap<String, PriorityQueue<ProcessingTrade>> buyTrades = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, PriorityQueue<ProcessingTrade>> sellTrades = new ConcurrentHashMap<>();

    @AllArgsConstructor
    private static class ProcessingTrade {
        Long id;
        Double price;
        TradeType tradeType;
    }

    public void addTrade(TradeType tradeType, String ticker, Long id, Double price) {
        buyTrades.putIfAbsent(ticker, new PriorityQueue<>((t1, t2) -> Double.compare(t2.price, t1.price))); // 최소힙
        sellTrades.putIfAbsent(ticker, new PriorityQueue<>((t1, t2) -> Double.compare(t1.price, t2.price))); // 최대힙

        if (tradeType.equals(BUY) || tradeType.equals(LONG)) {
            log.info("{} 가 {} 이하면 청산", ticker, price);
            buyTrades.get(ticker).add(new ProcessingTrade(id, price, tradeType));
        } else {
            sellTrades.get(ticker).add(new ProcessingTrade(id, price, tradeType));
        }
    }

    public Optional<Double> getCryptoPrice(String ticker) {
        return Optional.ofNullable(cryptoPrices.get(ticker));
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, Response response) {
        log.info("upbit websocket opened [response: {}]", response.body());
        log.info("이전에 등록된 감지될 거래 데이터들을 로딩합니다. ");

        List<LoadTrade> loadTrades = openTradeService.getLoadTrades();
        log.info("미체결 거래: {} 개 로딩", loadTrades.size());
        loadTrades.forEach(loadTrade -> addTrade(loadTrade.tradeType(), loadTrade.ticker(), loadTrade.id(), loadTrade.price()));

        loadTrades = liquidateService.getLoadTrades();
        log.info("활성 선물 거래: {} 개 로딩", loadTrades.size());
        loadTrades.forEach(loadTrade -> addTrade(loadTrade.tradeType(), loadTrade.ticker(), loadTrade.id(), loadTrade.price()));
    }

    /*
    *   crypto message processing
    * */
    @Override
    public void onMessage(@NotNull WebSocket webSocket, ByteString bytes) {
        String response = bytes.string(StandardCharsets.UTF_8);
        CryptoJson cryptoJson = httpClientUtil.parseJsonToClass(response, CryptoJson.class)
                .orElseThrow(() -> new TradeException(PRICE_CONNECTION_FAIL));

        priceUpdate(cryptoJson.getCode(), cryptoJson.getTrade_price());
    }

    private void priceUpdate(String code, Double tradePrice) {
        if (tradePrice.equals(cryptoPrices.get(code))) {
            return; // 가격이 같으면 업데이트 x
        }

        cryptoPrices.put(code, tradePrice);

        processTrade(code, tradePrice);
    }

    @Async
    public void processTrade(String code, Double tradePrice) {
        PriorityQueue<ProcessingTrade> buyQueue = buyTrades.get(code);
        while (Objects.nonNull(buyQueue) && !buyQueue.isEmpty() && buyQueue.peek().price >= tradePrice) {
            ProcessingTrade trade = buyQueue.poll();
            if (trade.tradeType.equals(BUY)) {
                log.info("현물 {} price:{} ,체결", code, tradePrice);
                openTradeService.processTrade(trade.id, tradePrice);
            } else {
                log.info("선물 {} price:{} ,체결", code, tradePrice);
                liquidateService.liquidateFutures(trade.id);
            }
        }

        PriorityQueue<ProcessingTrade> sellQueue = sellTrades.get(code);
        while (Objects.nonNull(sellQueue) && !sellQueue.isEmpty() && sellQueue.peek().price <= tradePrice) {
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

    @Override
    public void onFailure(@NotNull WebSocket webSocket, Throwable t, Response response) {
        log.error("upbit websocket error! msg:{}, response:{} ", t.getMessage(), response == null ? "null" : response.message());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, @NotNull String reason) {
        webSocket.close(1000, null);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        log.info("upbit websocket closed [code:{}, reason:{}]", code, reason);
    }

}
