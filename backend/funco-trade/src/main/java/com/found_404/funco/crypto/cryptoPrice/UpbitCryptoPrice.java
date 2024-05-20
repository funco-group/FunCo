package com.found_404.funco.crypto.cryptoPrice;

import com.found_404.funco.crypto.cryptoPrice.jsonObject.CryptoJson;
import com.found_404.funco.crypto.cryptoPrice.jsonObject.Format;
import com.found_404.funco.crypto.cryptoPrice.jsonObject.Ticket;
import com.found_404.funco.crypto.cryptoPrice.jsonObject.TypeCodes;
import com.found_404.funco.global.util.HttpClientUtil;
import com.found_404.funco.trade.exception.TradeException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.found_404.funco.trade.exception.TradeErrorCode.PRICE_CONNECTION_FAIL;

@Component
@Slf4j
public class UpbitCryptoPrice implements CryptoPrice {
    private static final String WEBSOCKET_URL = "wss://api.upbit.com/websocket/v1";
    private static final String PRICE_API_URL = "https://api.upbit.com/v1/ticker?markets=";
    private static final String MARKET_URL = "https://api.upbit.com/v1/market/all";
    // 설정으로 뺄 예정

    private final UpbitWebSocketListener listener;
    private final HttpClientUtil httpClientUtil;
    private WebSocket webSocket;

    @Autowired
    public UpbitCryptoPrice(UpbitWebSocketListener listener, HttpClientUtil httpClientUtil) {
        this.listener = listener;
        this.httpClientUtil = httpClientUtil;

        connectWebSocket();
    }

    private void connectWebSocket() {
        Request request = new Request.Builder().url(WEBSOCKET_URL).build();
        this.webSocket = httpClientUtil.getWebSocket(request, listener);
        sendWebSocketAllMarkets();
    }

    @Override
    public Map<String, Double> getTickerPriceMap(List<String> tickers) {
        if (tickers.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Double> map = new HashMap<>();
        for (String ticker : tickers) {
            Optional<Double> optional = listener.getCryptoPrice(ticker);
            if (optional.isEmpty()) {
                log.info(" {}의 실시간 시세가 없어서 API 요청합니다. ", ticker);
                break;
            }
            map.put(ticker, optional.get());
        }
        if (map.size() == tickers.size()) {
            return map;
        }

        String apiResponse = httpClientUtil.getApiResponse(getUrlWithParameters(tickers));
        if (Objects.isNull(apiResponse)) {
            throw new TradeException(PRICE_CONNECTION_FAIL);
        }

        return Arrays.stream(httpClientUtil.parseJsonToClass(apiResponse, CryptoJson[].class)
                        .orElseThrow(() -> new TradeException(PRICE_CONNECTION_FAIL)))
                .collect(Collectors.toMap(CryptoJson::getMarket, CryptoJson::getTrade_price));
    }

    @Override
    public double getTickerPrice(String ticker) {
        Optional<Double> optionalPrice = listener.getCryptoPrice(ticker);
        if (optionalPrice.isPresent()) {
            return optionalPrice.get();
        }

        // 없을 시 api 요청
        String apiResponse = httpClientUtil.getApiResponse(getUrlWithParameters(List.of(ticker)));
        if (Objects.isNull(apiResponse)) {
            throw new TradeException(PRICE_CONNECTION_FAIL);
        }

        CryptoJson[] cryptoJsons = httpClientUtil.parseJsonToClass(apiResponse, CryptoJson[].class)
                .orElseThrow(() -> new TradeException(PRICE_CONNECTION_FAIL));
        return cryptoJsons[0].getTrade_price();
    }

    private String getUrlWithParameters(List<String> tickers) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < tickers.size(); i++) {
            stringBuilder.append(tickers.get(i)).append(',');
        }
        stringBuilder.append(tickers.get(0));

        return PRICE_API_URL + stringBuilder;
    }

    private void sendWebSocketAllMarkets() {
        String apiResponse = httpClientUtil.getApiResponse(MARKET_URL);
        if (Objects.isNull(apiResponse)) {
            throw new TradeException(PRICE_CONNECTION_FAIL);
        }
        CryptoJson[] cryptoJsons = httpClientUtil.parseJsonToClass(apiResponse, CryptoJson[].class)
                .orElseThrow(() -> new TradeException(PRICE_CONNECTION_FAIL));

        List<String> markets = Arrays.stream(cryptoJsons).map(CryptoJson::getMarket)
                .filter(market -> market.startsWith("KRW")).toList();

        String message = httpClientUtil.toJson(List.of(new Ticket(),
                new TypeCodes(markets),
                new Format()));
        log.info("[upbit API] {}개의 코인 시세 웹소켓으로 받기 시작합니다.",markets.size());
        log.info("[websocket] send message: {}", message);
        webSocket.send(message);
    }

}
