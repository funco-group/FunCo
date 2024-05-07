package com.found_404.funco.trade.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.found_404.funco.trade.domain.OpenTrade;

public interface QueryDslOpenTradeRepository {

    List<OpenTrade> findMyOpenTrade(Long memberId, String ticker, Pageable pageable);
}
