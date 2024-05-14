package com.found_404.funco.follow.service;

import static com.found_404.funco.follow.exception.FollowErrorCode.*;
import static com.found_404.funco.global.util.DecimalCalculator.ScaleType.*;
import static com.found_404.funco.global.util.DecimalCalculator.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.found_404.funco.client.dto.CoinValuation;
import com.found_404.funco.client.dto.CoinValuationResponse;
import com.found_404.funco.client.service.MemberService;
import com.found_404.funco.client.service.TradeService;
import com.found_404.funco.follow.domain.Follow;
import com.found_404.funco.follow.domain.FollowTrade;
import com.found_404.funco.follow.domain.FollowingCoin;
import com.found_404.funco.follow.domain.repository.FollowRepository;
import com.found_404.funco.follow.domain.repository.FollowTradeRepository;
import com.found_404.funco.follow.domain.repository.FollowingCoinRepository;
import com.found_404.funco.follow.domain.type.TradeType;
import com.found_404.funco.follow.dto.FollowTradeDto;
import com.found_404.funco.follow.dto.HoldingCoinsDto;
import com.found_404.funco.follow.dto.SliceFollowingInfo;
import com.found_404.funco.follow.dto.request.FollowingRequest;
import com.found_404.funco.follow.dto.response.FollowerListResponse;
import com.found_404.funco.follow.dto.response.FollowingListResponse;
import com.found_404.funco.follow.exception.FollowException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowService {

	private final FollowRepository followRepository;
	private final FollowingCoinRepository followingCoinRepository;
	private final FollowTradeRepository followTradeRepository;

	private final MemberService memberService;
	private final TradeService tradeService;

	private static final double FOLLOW_FEE = 0.03;
	private static final int PAGE_SIZE = Integer.MAX_VALUE - 1; // 임시
	private static final long PERCENT = 100L;

	// private final HoldingCoinRepository holdingCoinRepository;
	// private final CryptoPrice cryptoPrice;
	// private final NotificationService notificationService;

	@Transactional
	public void createFollow(FollowingRequest request, Long memberId) {

		// 팔로우 하려는 대상이 본인이라면 예외
		if (request.memberId().equals(memberId)) {
			throw new FollowException(FOLLOW_SELF_ERROR);
		}

		// 부모 팔로우 멤버
		Long followingMemberId = request.memberId();

		// 자식 팔로우 멤버
		Long followerMemberId = request.memberId();

		// 팔로우 되어있다면 예외
		Optional<Follow> selectFollow = followRepository.findFollowByFollowingMemberIdAndFollowerMemberIdAndSettledFalse(
			followingMemberId, followerMemberId);
		if (selectFollow.isPresent()) {
			throw new FollowException(FOLLOW_DUPLICATED_ERROR);
		}

		// 초기 투자금
		Long investment = request.investment();

		// 부모 팔로워 가용 현금
		Long followingCash = memberService.getMemberCash(followingMemberId);

		CoinValuationResponse coinValuationResponse = tradeService.getCoinValuations(memberId);

		Long followingFollowSum = followRepository.findAllByFollowerMemberId(followingMemberId)
			.stream().map(Follow::getInvestment)
			.reduce(0L, Long::sum);

		// 부모 총 보유 자산 = 부모의 거래자산 총합 + 부모의 가용 현금 + 부모 팔로우 총 합
		long followingAsset = coinValuationResponse.totalTradeAsset() + followingCash + followingFollowSum;

		// 부모의 보유 자산의 비율
		Map<String, Double> followingAssetRatio = coinValuationResponse.coinValuations()
			.stream()
			.collect(Collectors.toMap(CoinValuation::ticker,
				coinValuation -> divide(coinValuation.valuation(), followingAsset, NORMAL_SCALE)));

		// 팔로우의 가용 현금 = 투자금 - 산 금액
		//Long followerCash = (long)multiple(investment, divide(followingCash, followingAsset, NORMAL_SCALE), CASH_SCALE);

		// 팔로우 생성
		Follow follow = Follow.builder()
			.followingMemberId(followingMemberId)
			.followerMemberId(followerMemberId)
			.investment(investment)
			.cash(followerCash)
			.settled(Boolean.FALSE)
			.build();

		// 팔로잉 코인, 거래 내역 생성
		/*
		 * 팔로잉 코인 갯수 = (초기 투자금 * 부모의 전체 자산에 대해 해당 코인이 차지하는 비율) / 해당 코인의 현재 시세
		 * 주문 가격 = 초기 투자금 * 부모의 해당 코인의 전체 자산에 대한 비율 => 부모의 코인 비율만큼 사는 것이기 때문
		 * */
		Map<FollowingCoin, FollowTrade> followingCoinFollowTradeMap = followingAssetRatio.entrySet().stream()
			.collect(Collectors.toMap(entry -> FollowingCoin.builder()
					.follow(follow)
					.ticker(entry.getKey())
					.volume(divide(multiple(investment, entry.getValue(), NORMAL_SCALE),
						followingCryptoPriceMap.get(entry.getKey()), VOLUME_SCALE))
					.averagePrice(followingCryptoPriceMap.get(entry.getKey()))
					.build(),
				entry -> FollowTrade.builder()
					.follow(follow)
					.ticker(entry.getKey())
					.tradeType(TradeType.BUY)
					.volume(
						divide(multiple(investment, entry.getValue(), NORMAL_SCALE),
							followingCryptoPriceMap.get(entry.getKey()), VOLUME_SCALE))
					.orderCash((long)multiple(investment, entry.getValue(), CASH_SCALE))
					.price(followingCryptoPriceMap.get(entry.getKey()))
					.build()));

		// 엔티티 insert
		followRepository.save(follow);
		followingCoinRepository.saveAll(followingCoinFollowTradeMap.keySet());
		followTradeRepository.saveAll(followingCoinFollowTradeMap.values());


		// 팔로워 초기 투자금 차감
		memberService.updateMemberCash(followerMemberId, -investment);

		// 알림
		// StringBuilder message = new StringBuilder();
		// message.append(followerMember.getNickname()).append("님에게 ")
		// 		.append(String.format("%,d", investment)).append("원을 투자 받으셨습니다.");
		// notificationService.sendNotification(followingMemberId, NotificationType.FOLLOW, message.toString());
	}

	@Transactional
	public void deleteFollow(Long followId) {
		// 팔로우
		Follow follow = followRepository.findById(followId).orElseThrow(() -> new FollowException(FOLLOW_NOT_FOUND));

		// 부모 팔로우 멤버
		Long followingMemberId = follow.getFollowingMemberId();

		// 자식 팔로우 멤버
		Long followerMemberId = follow.getFollowerMemberId();

		// 팔로잉 코인
		List<FollowingCoin> followingCoins = followingCoinRepository.findFollowingCoinsByFollow(follow);

		// 팔로잉 코인들의 현재 시세
		// Map<String, Long> followingCryptoPriceMap = cryptoPrice.getTickerPriceMap(followingCoins.stream()
		// 	.map(FollowingCoin::getTicker)
		// 	.collect(Collectors.toList()));
		Map<String, Long> followingCryptoPriceMap = new HashMap<>(); // 삭ㅈ


		// 거래 내역
		List<FollowTrade> followTrades = followingCoins.stream()
			.map(followingCoin -> FollowTrade.builder()
				.follow(follow)
				.ticker(followingCoin.getTicker())
				.tradeType(TradeType.SELL)
				.volume(followingCoin.getVolume())
				.orderCash(
					(long)multiple(followingCryptoPriceMap.get(followingCoin.getTicker()), followingCoin.getVolume(),
						CASH_SCALE))
				.price(followingCryptoPriceMap.get(followingCoin.getTicker()))
				.build())
			.toList();

		// 팔로잉 코인들 가격
		long followAsset = followingCoins.stream()
			.mapToLong(followingCoin ->
				(long)multiple(followingCryptoPriceMap.get(followingCoin.getTicker()), followingCoin.getVolume(),
					CASH_SCALE))
			.sum();

		// 수익금
		long proceed = followAsset + follow.getCash();

		// 수익률
		double returnRate = multiple(PERCENT,
			divide(proceed - follow.getInvestment(), follow.getInvestment(), NORMAL_SCALE), RETURN_RATE_SCALE);

		// 수수료
		long commission =
			proceed - follow.getInvestment() > 0 ?
				(long) multiple(proceed - follow.getInvestment(), FOLLOW_FEE, CASH_SCALE) : 0;

		// 정산 금액
		long settlement = proceed - commission;

		log.info("수익금 : {}", proceed);
		log.info("수익룰 : {}", returnRate);
		log.info("수수료 : {}", commission);
		log.info("정산 금액 : {}", settlement);

		// follow update
		follow.settleFollow(commission, returnRate, LocalDateTime.now(), settlement);

		//  정산 금액 멤버 재산 반영
		// follower member update
		// followerMember.settleCash(settlement);
		//
		// // following member update
		// if (commission > 0) {
		// 	followingMember.settleCash(commission);
		// }

		// 데이터 insert
		followingCoinRepository.deleteAll(followingCoins);
		followTradeRepository.saveAll(followTrades);

		// 알림
		// StringBuilder message = new StringBuilder();
		// message.append(followerMember.getNickname()).append("님이 투자금액 ")
		// 		.append(String.format("%,d", follow.getInvestment())).append("원을 정산하셨습니다. ")
		// 		.append(String.format("%,d", commission)).append("원의 수수료를 받았습니다.");
		// notificationService.sendNotification(followingMemberId, NotificationType.SETTLE, message.toString());

	}

	public FollowingListResponse readFollowingList(Long memberId, Long lastFollowId) {

		// 유저의 보유 코인 목록
		//List<HoldingCoinsDto> holdingCoins = followRepository.findHoldingCoin(memberId);
		List<HoldingCoinsDto> holdingCoins = new ArrayList<>();

		// 유저의 보유 코인 목록별 현재 시세
		// Map<String, Long> tickerPriceMap = cryptoPrice.getTickerPriceMap(
		// 	holdingCoins.stream()
		// 		.map(HoldingCoinsDto::ticker)
		// 		.collect(Collectors.toList()));
		Map<String, Long> tickerPriceMap = new HashMap<>(); // ㅅ

		// 유저의 보유 코인들의 현재 총 가격
		// Long totalAsset = holdingCoins.stream()
		// 	.mapToLong(holdingCoin -> (long)multiple(tickerPriceMap.get(holdingCoin.ticker()), holdingCoin.volume(),
		// 		CASH_SCALE))
		// 	.sum() + memberId.getCash();
		Long totalAsset = 0L;

		// 유저의 팔로우 자산 목록들
		SliceFollowingInfo sliceFollowingInfo = followRepository.findFollowingInfoListByMemberId(memberId,
			lastFollowId, PAGE_SIZE);

		return FollowingListResponse.builder()
			.totalAsset(totalAsset)
			.followings(sliceFollowingInfo.followingInfoList())
			.last(sliceFollowingInfo.last())
			.build();
	}

	public FollowerListResponse readFollowerList(Long memberId, String settled, Long lastFollowId) {
		return followRepository.findFollowerListByMemberIdAndSettleType(memberId, settled, lastFollowId, PAGE_SIZE);
	}

	public List<FollowTradeDto> getFollowTrades(Pageable pageable, Long followId) {
		Follow follow = followRepository.getReferenceById(followId);

		return followTradeRepository.findByFollow(pageable, follow)
				.stream()
				.map(FollowTradeDto::fromEntity)
				.toList();
	}
}
