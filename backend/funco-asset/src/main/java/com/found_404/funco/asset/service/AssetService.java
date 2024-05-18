package com.found_404.funco.asset.service;

import static com.found_404.funco.asset.domain.type.PeriodType.*;
import static com.found_404.funco.asset.exception.AssetErrorCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.found_404.funco.asset.domain.AssetHistory;
import com.found_404.funco.asset.domain.repository.AssetHistoryRepository;
import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.asset.domain.type.PeriodType;
import com.found_404.funco.asset.domain.type.TradeType;
import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
import com.found_404.funco.asset.dto.response.CryptoResponse;
import com.found_404.funco.asset.dto.response.TotalAssetResponse;
import com.found_404.funco.asset.exception.AssetException;
import com.found_404.funco.feignClient.service.FollowService;
import com.found_404.funco.feignClient.service.MemberService;
import com.found_404.funco.feignClient.service.TradeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetService {

	private final AssetHistoryRepository assetHistoryRepository;
	private final MemberService memberService;
	private final TradeService tradeService;
	private final FollowService followService;

	/*
	 * v1 대비 v2 추가 사항
	 * 선물 거래 내역 추가
	 * 이외는 동일
	 * */
	public TotalAssetResponse getMemberTotalAsset(Long memberId) {
		//
		// Member member = findByMemberId(memberId);
		//
		// // 가용 현금
		// Long memberCash = member.getCash();
		//
		// // 해당 멤버가 팔로우 중인 총 초기 투자 금액
		// List<Follow> follows = followRepository.findAllByFollowerAndSettledFalse(member);
		// Long memberFollwingInvestment = follows.stream()
		// 	.mapToLong(Follow::getInvestment)
		// 	.sum();
		//
		// // 해당 멤버가 보유 중인 코인
		// // HoldingCoinInfo에 담아서 응답으로 컨트롤러에 넘겨줌
		// List<HoldingCoin> holdingCoins = holdingCoinRepository.findHoldingCoinByMember(member);
		// List<HoldingCoinInfo> memberHoldingCoinInfos = holdingCoins.stream()
		// 	.map(holdingCoin -> new HoldingCoinInfo(
		// 		holdingCoin.getTicker(),
		// 		holdingCoin.getVolume(),
		// 		holdingCoin.getAveragePrice()
		// 	))
		// 	.toList();
		//
		// // 해당 멤버가 보유 중인 선물 거래
		// List<ActiveFuture> activeFutures = activeFutureRepository.findActiveFutureByMember(member);
		// List<ActiveFutureInfo> memberActiveFutureInfos = activeFutures.stream()
		// 	.map(activeFuture -> new ActiveFutureInfo(
		// 		activeFuture.getTicker(),
		// 		activeFuture.getTradeType(),
		// 		activeFuture.getOrderCash(),
		// 		activeFuture.getPrice(),
		// 		activeFuture.getLeverage()
		// 	))
		// 	.toList();

		return TotalAssetResponse.builder()
			// .cash(memberCash)
			// .followingInvestment(memberFollwingInvestment)
			// .holdingCoinInfos(memberHoldingCoinInfos)
			// .activeFutureInfos(memberActiveFutureInfos)
			.build();
	}

	public CryptoResponse getCrypto(Long member, String ticker) {
		// Optional<HoldingCoin> optionalHoldingCoin = holdingCoinRepository.findByMemberAndTicker(member, ticker);
		// return new CryptoResponse(optionalHoldingCoin.isPresent() ? optionalHoldingCoin.get().getVolume() : 0);
		return null;
	}

	public List<? extends AssetHistoryResponse> getMemberHistory(Long memberId, PeriodType period, AssetType asset,
		TradeType tradeType) {

		LocalDateTime endDateTime = LocalDateTime.now();
		LocalDate endDate = LocalDate.now();

		LocalDateTime startDateTime = switch (period.name()) {
			case "DAY" -> endDate.atStartOfDay();
			case "WEEK" -> endDate.minusWeeks(WEEK.number).atStartOfDay();
			case "ONEMONTH" -> endDate.minusMonths(ONEMONTH.number).atStartOfDay();
			case "THREEMONTH" -> endDate.minusMonths(THREEMONTH.number).atStartOfDay();
			case "SIXMONTH" -> endDate.minusMonths(SIXMONTH.number).atStartOfDay();
			default -> null;
		};

		return switch (asset.name()) {
			case "COIN" -> assetHistoryRepository.findCoinHistory(memberId, startDateTime, endDateTime, tradeType);
			case "FOLLOW" -> assetHistoryRepository.findFollowHistory(memberId, startDateTime, endDateTime, tradeType);
			case "PORTFOLIO" ->
				assetHistoryRepository.findPortfolioHistory(memberId, startDateTime, endDateTime, tradeType);
			default -> Collections.emptyList();
		};
	}

	public void initializeMemberCash(Long memberId) {
		LocalDateTime memberInitCashDate = memberService.getInitCashDate(memberId).initCashDate();
		LocalDateTime now = LocalDateTime.now();
		if (Objects.nonNull(memberInitCashDate) && now.isBefore(memberInitCashDate.plusHours(24))) {
			throw new AssetException(INIT_NOT_ALLOWED);
		}

		// 강제 정산
		tradeService.removeCoins(memberId);
		followService.modifyFollowingAndFollower(memberId);

		// 원 초기화 날짜 업데이트
		memberService.modifyCashAndInitCashDate(memberId);
	}

	public void saveCoinToAssetHistory(Long memberId, String ticker, TradeType tradeType, Double volume,
		Long price, Long orderCash, Long beginningCash, Long endingCash) {

		// 코인 거래 시 assetHistory에 필요한 요소들 저장
		assetHistoryRepository.save(
			AssetHistory.builder()
				.memberId(memberId)
				.ticker(ticker)
				.assetType(AssetType.COIN)
				.tradeType(tradeType)
				.volume(volume)
				.price(price)
				.orderCash(orderCash)
				.beginningCash(beginningCash)
				.endingCash(endingCash)
				.build()
		);

	}

	public void saveFollowToAssetHistory(Long memberId, TradeType tradeType, String followName,
		Long investment, Double returnRate, Long commission, Long settlement, LocalDateTime followDate,
		Long beginningCash, Long endingCash) {

		// 코인 거래 시 assetHistory에 필요한 요소들 저장
		assetHistoryRepository.save(
			AssetHistory.builder()
				.memberId(memberId)
				.assetType(AssetType.FOLLOW)
				.tradeType(tradeType)
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

	public void savePortfolioToAssetHistory(Long memberId, String portfolioName, TradeType tradeType,
		Long price, Long beginningCash, Long endingCash) {

		// 코인 거래 시 assetHistory에 필요한 요소들 저장
		assetHistoryRepository.save(
			AssetHistory.builder()
				.memberId(memberId)
				.assetType(AssetType.PORTFOLIO)
				.tradeType(tradeType)
				.portfolioName(portfolioName)
				.price(price)
				.beginningCash(beginningCash)
				.endingCash(endingCash)
				.build()
		);

	}

}
