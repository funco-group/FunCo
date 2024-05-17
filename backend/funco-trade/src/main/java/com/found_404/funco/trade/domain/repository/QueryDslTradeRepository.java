package com.found_404.funco.trade.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.dto.RecentTradedCoin;

public interface QueryDslTradeRepository {

    // 멤버, 티커 or all, update 역순 정렬, 페이징
    List<Trade> findMyTradeHistoryByTicker(Long memberId, String ticker, Pageable pageable);

    List<RecentTradedCoin> findRecentTradedCoinByMemberId(Long memberId);
}
