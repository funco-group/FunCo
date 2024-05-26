package com.found_404.funco.crypto.cryptoPrice;

import java.util.List;
import java.util.Map;

public interface CryptoPrice {
    Map<String, Double> getTickerPriceMap(List<String> tickers) throws RuntimeException;

    double getTickerPrice(String ticker) throws RuntimeException;

}
