package com.found_404.funco.crypto.cryptoPrice;

import com.found_404.funco.crypto.LiveTradeProcessor;
import com.found_404.funco.crypto.cryptoPrice.jsonObject.CryptoJson;
import com.found_404.funco.global.util.HttpClientUtil;
import com.found_404.funco.trade.exception.TradeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.found_404.funco.trade.exception.TradeErrorCode.PRICE_CONNECTION_FAIL;

@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitWebSocketListener extends WebSocketListener {
    private final HttpClientUtil httpClientUtil;
    private final LiveTradeProcessor liveTradeProcessor;

    private final Map<String, Double> cryptoPrices = new HashMap<>();

    public Optional<Double> getCryptoPrice(String ticker) {
        return Optional.ofNullable(cryptoPrices.get(ticker));
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, Response response) {
        log.info("upbit websocket opened [response: {}]", response.body());
        liveTradeProcessor.loadTradeData();
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

        liveTradeProcessor.processTrade(code, tradePrice);
        cryptoPrices.put(code, tradePrice);
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
