package com.found_404.funco.trade.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.trade.domain.HoldingCoin;

public interface HoldingCoinRepository extends JpaRepository<HoldingCoin, Long> {
	Optional<HoldingCoin> findByMemberIdAndTicker(Long memberId, String ticker);

	List<HoldingCoin> findByMemberId(Long memberId);

	List<HoldingCoin> findHoldingCoinByMemberId(Long memberId);
}
