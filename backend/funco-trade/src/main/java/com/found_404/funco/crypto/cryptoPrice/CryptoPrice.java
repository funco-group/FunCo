package com.found_404.funco.crypto.cryptoPrice;

import java.util.List;
import java.util.Map;

import com.found_404.funco.trade.domain.type.TradeType;

public interface CryptoPrice {
    Map<String, Double> getTickerPriceMap(List<String> tickers) throws RuntimeException;

    double getTickerPrice(String ticker) throws RuntimeException;

    void addTrade(String ticker, Long id, TradeType tradeType, Double price);

}
