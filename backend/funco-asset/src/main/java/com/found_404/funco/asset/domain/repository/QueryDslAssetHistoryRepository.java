package com.found_404.funco.asset.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.found_404.funco.asset.domain.type.AssetTradeType;
import com.found_404.funco.asset.dto.CoinHistory;
import com.found_404.funco.asset.dto.FollowHistory;
import com.found_404.funco.asset.dto.FuturesHistory;
import com.found_404.funco.asset.dto.PortfolioHistory;

public interface QueryDslAssetHistoryRepository {
	List<CoinHistory> findCoinHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate, AssetTradeType assetTradeType);
	List<FollowHistory> findFollowHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate, AssetTradeType assetTradeType);
	List<PortfolioHistory> findPortfolioHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate, AssetTradeType assetTradeType);
	List<FuturesHistory> findFuturesHistory(Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime, AssetTradeType assetTradeType);
}
