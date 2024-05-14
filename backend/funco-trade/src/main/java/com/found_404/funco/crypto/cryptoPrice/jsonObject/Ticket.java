package com.found_404.funco.crypto.cryptoPrice.jsonObject;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Ticket {
    private final String ticket = UUID.randomUUID().toString();
}
