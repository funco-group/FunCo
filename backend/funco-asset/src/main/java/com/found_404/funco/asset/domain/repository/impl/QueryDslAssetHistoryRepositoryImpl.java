package com.found_404.funco.asset.domain.repository.impl;

import static com.found_404.funco.asset.domain.QAssetHistory.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.found_404.funco.asset.dto.*;
import org.springframework.stereotype.Repository;

import com.found_404.funco.asset.domain.repository.QueryDslAssetHistoryRepository;
import com.found_404.funco.asset.domain.type.TradeType;
import com.found_404.funco.asset.domain.type.AssetType;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class QueryDslAssetHistoryRepositoryImpl implements QueryDslAssetHistoryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<CoinHistory> findCoinHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate,
											 TradeType tradeType) {

		return jpaQueryFactory
			.select(new QCoinHistory(assetHistory.createdAt, assetHistory.ticker, assetHistory.tradeType,
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
	public List<FollowHistory> findFollowHistory(Long memberId, LocalDateTime startDate, LocalDateTime endDate,
												 TradeType tradeType) {
		return jpaQueryFactory
			.select(new QFollowHistory(assetHistory.createdAt, assetHistory.tradeType, assetHistory.investment,
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
	public List<PortfolioHistory> findPortfolioHistory(Long memberId, LocalDateTime startDate,
													   LocalDateTime endDate, TradeType tradeType) {
		return jpaQueryFactory
			.select(new QPortfolioHistory(assetHistory.createdAt, assetHistory.portfolioName, assetHistory.tradeType,
				assetHistory.price, assetHistory.endingCash))
			.from(assetHistory)
			.where(assetHistory.memberId.eq(memberId),
				assetHistory.assetType.eq(AssetType.PORTFOLIO),
				filterDate(startDate, endDate),
				filterType(tradeType))
			.orderBy(assetHistory.createdAt.desc())
			.fetch();
	}

	@Override
	public List<FuturesHistory> findFuturesHistory(Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime, TradeType tradeType) {
		return jpaQueryFactory
				.select(new QFuturesHistory(assetHistory.createdAt, assetHistory.ticker, assetHistory.tradeType
						, assetHistory.price, assetHistory.orderCash, assetHistory.endingCash))
				.from(assetHistory)
				.where(assetHistory.memberId.eq(memberId),
						assetHistory.assetType.eq(AssetType.COIN),
						filterDate(startDateTime, endDateTime),
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
