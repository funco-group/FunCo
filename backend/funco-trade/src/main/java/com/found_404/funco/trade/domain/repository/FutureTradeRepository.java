package com.found_404.funco.trade.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.trade.domain.FutureTrade;

import java.util.List;

public interface FutureTradeRepository extends JpaRepository<FutureTrade, Long> {
    List<FutureTrade> findAllByMemberIdAndTickerOrderByIdDesc(Long memberId, String ticker);
}
