package com.found_404.funco.trade.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.trade.domain.FutureTrade;

public interface FutureTradeRepository extends JpaRepository<FutureTrade, Long> {
}
