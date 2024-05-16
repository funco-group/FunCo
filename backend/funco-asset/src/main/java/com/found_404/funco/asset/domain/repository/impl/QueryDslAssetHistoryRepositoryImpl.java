package com.found_404.funco.asset.domain.repository.impl;

import static com.found_404.funco.asset.domain.QAssetHistory.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.found_404.funco.asset.domain.repository.QueryDslAssetHistoryRepository;
import com.found_404.funco.asset.domain.type.TradeType;
import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.asset.dto.response.CoinHistoryResponse;
import com.found_404.funco.asset.dto.response.FollowHistoryResponse;
import com.found_404.funco.asset.dto.response.PortfolioHistoryResponse;
import com.found_404.funco.asset.dto.response.QCoinHistoryResponse;
import com.found_404.funco.asset.dto.response.QFollowHistoryResponse;
import com.found_404.funco.asset.dto.response.QPortfolioHistoryResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class QueryDslAssetHistoryRepositoryImpl implements QueryDslAssetHistoryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<CoinHistoryResponse> findCoinHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate,
		TradeType tradeType) {

		return jpaQueryFactory
			.select(new QCoinHistoryResponse(assetHistory.createdAt, assetHistory.ticker, assetHistory.tradeType,
				assetHistory.volume, assetHistory.price, assetHistory.orderCash, assetHistory.endingCash))
			.from(assetHistory)
			.where(assetHistory.memberId.eq(memberId),
				assetHistory.assetType.eq(AssetType.COIN),
				filterDate(startDate, endDate),
				filterType(tradeType))
			.orderBy(assetHistory.createdAt.desc())
			.fetch();
	}

	@Override
	public List<FollowHistoryResponse> findFollowHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate,
		TradeType tradeType) {
		return jpaQueryFactory
			.select(new QFollowHistoryResponse(assetHistory.createdAt, assetHistory.tradeType, assetHistory.followName, assetHistory.investment,
				assetHistory.settlement, assetHistory.followReturnRate, assetHistory.commission, assetHistory.followDate))
			.from(assetHistory)
			.where(assetHistory.memberId.eq(memberId),
				assetHistory.assetType.eq(AssetType.FOLLOW),
				filterDate(startDate, endDate),
				filterType(tradeType))
			.orderBy(assetHistory.createdAt.desc())
			.fetch();
	}

	@Override
	public List<PortfolioHistoryResponse> findPortfolioHistory(Long memberId, LocalDateTime startDate,
		LocalDateTime endDate, TradeType tradeType) {
		return jpaQueryFactory
			.select(new QPortfolioHistoryResponse(assetHistory.createdAt, assetHistory.portfolioName, assetHistory.tradeType,
				assetHistory.price, assetHistory.endingCash))
			.from(assetHistory)
			.where(assetHistory.memberId.eq(memberId),
				assetHistory.assetType.eq(AssetType.PORTFOLIO),
				filterDate(startDate, endDate),
				filterType(tradeType))
			.orderBy(assetHistory.createdAt.desc())
			.fetch();
	}


	private Predicate filterDate(LocalDateTime startDate, LocalDateTime endDate) {
		return Objects.nonNull(startDate) && Objects.nonNull(endDate) ? assetHistory.createdAt.between(startDate, endDate) : null;
	}

	private Predicate filterType(TradeType tradeType) {
		return assetHistory.tradeType.eq(tradeType);
	}


}
