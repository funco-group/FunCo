package com.found_404.funco.trade.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.trade.domain.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long>, QueryDslTradeRepository {

    // 직접 투자하는 코인만 가져옴
    List<Trade> findAllByMemberId(Long memberId);

}
