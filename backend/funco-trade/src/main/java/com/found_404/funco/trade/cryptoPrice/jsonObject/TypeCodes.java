package com.found_404.funco.trade.cryptoPrice.jsonObject;

import java.util.List;

import lombok.Getter;

@Getter
public class TypeCodes {
    private final String type = "trade";
    private final List<String> codes; // market

    public TypeCodes(List<String> codes) {
        this.codes = codes;
    }
}
