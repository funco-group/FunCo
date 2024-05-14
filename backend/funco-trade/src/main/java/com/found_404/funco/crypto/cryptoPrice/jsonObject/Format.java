package com.found_404.funco.crypto.cryptoPrice.jsonObject;

import lombok.Getter;

@Getter
public class Format {
    private final String format;

    public Format() {
        this.format = "DEFAULT";
    }

    public Format(String format) {
        this.format = format;
    }
}
