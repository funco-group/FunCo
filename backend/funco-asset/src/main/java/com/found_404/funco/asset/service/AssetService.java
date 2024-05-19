package com.found_404.funco.asset.service;

import static com.found_404.funco.asset.domain.type.PeriodType.*;
import static com.found_404.funco.asset.exception.AssetErrorCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.found_404.funco.asset.dto.*;
import com.found_404.funco.asset.dto.request.TotalAssetHistoryRequest;
import org.springframework.stereotype.Service;

import com.found_404.funco.asset.domain.AssetHistory;
import com.found_404.funco.asset.domain.repository.AssetHistoryRepository;
import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.asset.domain.type.PeriodType;
import com.found_404.funco.asset.domain.type.TradeType;
import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
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

		// 가용 현금
		Long memberCash = memberService.getCash(memberId).cash();

		// 해당 멤버가 팔로우 중인 총 초기 투자 금액
		Long memberFollowingInvestment = followService.getFollowingInvestment(memberId).investments();

		// 해당 멤버가 보유 중인 코인
		List<HoldingCoinInfo> memberHoldingCoinInfos = tradeService.getAssetHoldingCoin(memberId);

		// 해당 멤버가 보유 중인 선물 거래
		List<ActiveFutureInfo> memberActiveFutureInfos = tradeService.getAssetFuture(memberId);

		return TotalAssetResponse.builder()
			.cash(memberCash)
			.followingInvestment(memberFollowingInvestment)
			.holdingCoinInfos(memberHoldingCoinInfos)
			.activeFutureInfos(memberActiveFutureInfos)
			.build();
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
			case "FUTURES" -> assetHistoryRepository.findFuturesHistory(memberId, startDateTime, endDateTime, tradeType);
			case "FOLLOW" -> assetHistoryRepository.findFollowHistory(memberId, startDateTime, endDateTime, tradeType);
			case "PORTFOLIO" -> assetHistoryRepository.findPortfolioHistory(memberId, startDateTime, endDateTime, tradeType);
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

	public void saveDataToAssetHistory(TotalAssetHistoryRequest totalAssetHistoryRequest) {
		assetHistoryRepository.save(AssetHistory.builder()
				.memberId(totalAssetHistoryRequest.memberId())
				.assetType(totalAssetHistoryRequest.assetType())
				.tradeType(totalAssetHistoryRequest.tradeType())
				.volume(totalAssetHistoryRequest.volume())
				.price(totalAssetHistoryRequest.price())
				.commission(totalAssetHistoryRequest.commission())
				.settlement(totalAssetHistoryRequest.settlement())
				.beginningCash(totalAssetHistoryRequest.beginningCash())
				.endingCash(totalAssetHistoryRequest.endingCash())
				.orderCash(totalAssetHistoryRequest.orderCash())
				.ticker(totalAssetHistoryRequest.ticker())
				.portfolioName(totalAssetHistoryRequest.portfolioName())
				.investment(totalAssetHistoryRequest.investment())
				.followName(totalAssetHistoryRequest.followName())
				.followDate(totalAssetHistoryRequest.followDate())
				.followReturnRate(totalAssetHistoryRequest.followReturnRate())
				.build());
	}

}
