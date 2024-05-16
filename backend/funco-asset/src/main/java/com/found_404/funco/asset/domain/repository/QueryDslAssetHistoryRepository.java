package com.found_404.funco.asset.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.found_404.funco.asset.domain.type.TradeType;
import com.found_404.funco.asset.dto.response.CoinHistoryResponse;
import com.found_404.funco.asset.dto.response.FollowHistoryResponse;
import com.found_404.funco.asset.dto.response.PortfolioHistoryResponse;

public interface QueryDslAssetHistoryRepository {
	List<CoinHistoryResponse> findCoinHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate, TradeType tradeType);
	List<FollowHistoryResponse> findFollowHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate, TradeType tradeType);
	List<PortfolioHistoryResponse> findPortfolioHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate, TradeType tradeType);
}
