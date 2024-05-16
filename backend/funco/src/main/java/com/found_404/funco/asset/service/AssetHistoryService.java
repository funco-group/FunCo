package com.found_404.funco.asset.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.found_404.funco.asset.domain.AssetHistory;
import com.found_404.funco.asset.domain.repository.AssetHistoryRepository;
import com.found_404.funco.asset.domain.type.AssetTradeType;
import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetHistoryService {

	private final AssetHistoryRepository assetHistoryRepository;
	public void saveCoinToAssetHistory(Member member, String ticker, AssetTradeType tradeType, Double volume,
		Long price, Long orderCash, Long beginningCash, Long endingCash) {

		// 코인 거래 시 assetHistory에 필요한 요소들 저장
		assetHistoryRepository.save(
			AssetHistory.builder()
				.member(member)
				.ticker(ticker)
				.assetType(AssetType.COIN)
				.assetTradeType(tradeType)
				.volume(volume)
				.price(price)
				.orderCash(orderCash)
				.beginningCash(beginningCash)
				.endingCash(endingCash)
				.build()
		);

	}

	public void saveFollowToAssetHistory(Member member, AssetTradeType tradeType, String followName,
		Long investment, Double returnRate, Long commission, Long settlement, LocalDateTime followDate, Long beginningCash, Long endingCash) {

		// 코인 거래 시 assetHistory에 필요한 요소들 저장
		assetHistoryRepository.save(
			AssetHistory.builder()
				.member(member)
				.assetType(AssetType.FOLLOW)
				.assetTradeType(tradeType)
				.followName(followName)
				.investment(investment)
				.followReturnRate(returnRate)
				.commission(commission)
				.settlement(settlement)
				.followDate(followDate)
				.beginningCash(beginningCash)
				.endingCash(endingCash)
				.build()
		);

	}

	public void savePortfolioToAssetHistory(Member member, String portfolioName, AssetTradeType tradeType,
		Long price, Long beginningCash, Long endingCash) {

		// 코인 거래 시 assetHistory에 필요한 요소들 저장
		assetHistoryRepository.save(
			AssetHistory.builder()
				.member(member)
				.assetType(AssetType.PORTFOLIO)
				.assetTradeType(tradeType)
				.portfolioName(portfolioName)
				.price(price)
				.beginningCash(beginningCash)
				.endingCash(endingCash)
				.build()
		);

	}

}
