package com.found_404.funco.follow.service;

import static com.found_404.funco.follow.exception.FollowErrorCode.*;
import static com.found_404.funco.global.util.DecimalCalculator.ScaleType.*;
import static com.found_404.funco.global.util.DecimalCalculator.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.found_404.funco.feignClient.dto.*;
import com.found_404.funco.feignClient.service.AssetService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.feignClient.service.MemberService;
import com.found_404.funco.feignClient.service.NotificationService;
import com.found_404.funco.feignClient.service.TradeService;
import com.found_404.funco.follow.domain.Follow;
import com.found_404.funco.follow.domain.FollowTrade;
import com.found_404.funco.follow.domain.FollowingCoin;
import com.found_404.funco.follow.domain.repository.FollowRepository;
import com.found_404.funco.follow.domain.repository.FollowTradeRepository;
import com.found_404.funco.follow.domain.repository.FollowingCoinRepository;
import com.found_404.funco.follow.domain.type.TradeType;
import com.found_404.funco.follow.dto.FollowTradeDto;
import com.found_404.funco.follow.dto.FollowerList;
import com.found_404.funco.follow.dto.FollowingInfo;
import com.found_404.funco.follow.dto.RatioPrice;
import com.found_404.funco.follow.dto.SliceFollowingInfo;
import com.found_404.funco.follow.dto.request.FollowerProfitRequest;
import com.found_404.funco.follow.dto.request.FollowingRequest;
import com.found_404.funco.follow.dto.response.FollowAssetResponse;
import com.found_404.funco.follow.dto.response.FollowStatusResponse;
import com.found_404.funco.follow.dto.response.FollowerInfoResponse;
import com.found_404.funco.follow.dto.response.FollowerListResponse;
import com.found_404.funco.follow.dto.response.FollowerResponse;
import com.found_404.funco.follow.dto.response.FollowingListResponse;
import com.found_404.funco.follow.dto.response.FollowingResponse;
import com.found_404.funco.follow.dto.response.InvestmentsResponse;
import com.found_404.funco.follow.exception.FollowException;

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
	private final NotificationService notificationService;
	private final AssetService assetService;

	private static final double FOLLOW_FEE = 0.03, PERCENT = 100L;
	private static final int PAGE_SIZE = Integer.MAX_VALUE - 1; // 임시

	/*
	 *	부모 = 팔로우 당하는 사람, 팔로잉
	 *	자식 = 팔로우 하는 사람, 팔로워
	 * */
	@Transactional
	public void createFollow(FollowingRequest request, Long followerMemberId) {

		// 팔로우 하려는 대상이 본인이라면, 이미 팔로우 되어있다면 예외 예외
		checkSelfFollow(request, followerMemberId);
		checkDuplicatedFollow(request.memberId(), followerMemberId);

		// 부모 팔로우 멤버
		Long followingMemberId = request.memberId();

		// 초기 투자금
		Long investment = request.investment();

		// [API select] 부모 가용 현금
		Long followingCash = memberService.getMemberCash(followingMemberId);

		// [API select] 부모 투자정보
		CoinValuationResponse coinValuationResponse = tradeService.getCoinValuations(followerMemberId);

		// 부모의 팔로우 투자금 총액
		Long followingTotalFollowInvestment = getTotalFollowInvestment(followingMemberId);

		// 부모 총 보유 자산 = 부모의 거래자산 총합 + 부모의 가용 현금 + 부모의 팔로우 투자금 합
		long followingAsset = coinValuationResponse.totalTradeAsset() + followingCash + followingTotalFollowInvestment;

		// 부모의 보유 자산의 비율
		Map<String, RatioPrice> followingAssetRatio = coinValuationResponse.coinValuations()
			.stream()
			.collect(Collectors.toMap(CoinValuation::ticker,
				coinValuation -> new RatioPrice(divide(coinValuation.valuation(), followingAsset, NORMAL_SCALE),
					coinValuation.price())));

		// 팔로우 생성
		Follow follow = Follow.builder()
			.followingMemberId(followingMemberId)
			.followerMemberId(followerMemberId)
			.investment(investment)
			.cash(investment) // 처음엔 초기 투자금
			.settled(Boolean.FALSE)
			.build();

		/*
		 * 팔로잉 코인 갯수 = (초기 투자금 * 부모의 전체 자산에 대해 해당 코인이 차지하는 비율) / 해당 코인의 현재 시세
		 * 주문 가격 = 초기 투자금 * 부모의 해당 코인의 전체 자산에 대한 비율 => 부모의 코인 비율만큼 사는 것이기 때문
		 * */
		List<FollowingCoin> followingCoins = new ArrayList<>();
		List<FollowTrade> followTrades = new ArrayList<>();

		followingAssetRatio.forEach((ticker, value) -> {
			FollowingCoin newCoin = FollowingCoin.builder()
				.follow(follow)
				.ticker(ticker)
				.volume(divide(
					multiple(investment, value.ratio(), NORMAL_SCALE),
					value.price(), VOLUME_SCALE))
				.averagePrice(value.price())
				.build();
			followingCoins.add(newCoin);
			followTrades.add(getFollowTrade(follow, newCoin));
		});

		// 엔티티 insert
		followRepository.save(follow);
		followingCoinRepository.saveAll(followingCoins);
		followTradeRepository.saveAll(followTrades);

		// 산 만큼 팔로우 현금 차감
		follow.decreaseCash(followTrades.stream()
			.map(FollowTrade::getOrderCash)
			.reduce(0L, Long::sum));

		// [API update] 팔로워 자산 차감
		memberService.updateMemberCash(followerMemberId, -investment);

		// [API] async 팔로우 알림
		StringBuilder message = new StringBuilder();
		message.append(memberService.getSimpleMember(followerMemberId).nickname()).append("님에게 ")
			.append(String.format("%,d", investment)).append("원을 투자 받으셨습니다.");

		notificationService.sendNotification(followingMemberId, NotificationType.FOLLOW, message.toString());
	}

	private Long getTotalFollowInvestment(Long followingMemberId) {
		return followRepository.findAllByFollowerMemberId(followingMemberId)
			.stream().map(Follow::getInvestment)
			.reduce(0L, Long::sum);
	}

	private void checkSelfFollow(FollowingRequest request, Long followerMemberId) {
		if (Objects.equals(request.memberId(), followerMemberId)) {
			throw new FollowException(FOLLOW_SELF_ERROR);
		}
	}

	private void checkDuplicatedFollow(Long followingMemberId, Long followerMemberId) {
		followRepository.findFollowByFollowingMemberIdAndFollowerMemberIdAndSettledFalse(
			followingMemberId, followerMemberId).ifPresent(follow -> {
			throw new FollowException(FOLLOW_DUPLICATED_ERROR);
		});
	}

	private FollowTrade getFollowTrade(Follow follow, FollowingCoin coin) {
		return FollowTrade.builder()
			.follow(follow)
			.ticker(coin.getTicker())
			.tradeType(TradeType.BUY)
			.volume(coin.getVolume())
			.orderCash((long)multiple(coin.getAveragePrice(), coin.getVolume(), CASH_SCALE))
			.price(coin.getAveragePrice())
			.build();
	}

	@Transactional
	public void deleteFollow(Long followId) {
		// 팔로우
		Follow follow = getFollow(followId);

		// 팔로잉 코인
		List<FollowingCoin> followingCoins = followingCoinRepository.findFollowingCoinsByFollow(follow);

		// [API select] 팔로잉 코인들의 현재 시세
		Map<String, Double> cryptoPrice = tradeService.getCryptoPrice(
			followingCoins.stream()
				.map(FollowingCoin::getTicker)
				.toList());

		// 거래 내역
		List<FollowTrade> followTrades = followingCoins.stream()
			.map(followingCoin -> FollowTrade.builder()
				.follow(follow)
				.ticker(followingCoin.getTicker())
				.tradeType(TradeType.SELL)
				.volume(followingCoin.getVolume())
				.orderCash(
					(long)multiple(cryptoPrice.get(followingCoin.getTicker()), followingCoin.getVolume(), CASH_SCALE))
				.price(cryptoPrice.get(followingCoin.getTicker()))
				.build())
			.toList();

		// 팔로잉 코인들 가격
		long sellProfits = followTrades.stream()
			.map(FollowTrade::getOrderCash)
			.reduce(0L, Long::sum);

		// 수익금
		long proceed = sellProfits + follow.getCash();

		// 수익률
		double returnRate = getReturnRate(proceed, follow.getInvestment());

		// 수수료
		long commission = getCommission(proceed, follow.getInvestment());

		// 정산 금액
		long settlement = proceed - commission;
		log.info("정산 => follow:{} 수익금:{}, 수수료:{}, 수익률:{}", followId, settlement, commission, returnRate);

		// update follow is settled
		follow.settleFollow(commission, returnRate, settlement);

		// 데이터 insert
		followingCoinRepository.deleteAll(followingCoins);
		followTradeRepository.saveAll(followTrades);

		// [API update] 자산 반영
		Long endingCash = memberService.updateMemberCash(follow.getFollowerMemberId(), settlement);
		assetService.createAssetHistory(follow, AssetTradeType.FOLLOWING , endingCash); // 정산

		if (commission > 0) {
			Long endingCashByCommission = memberService.updateMemberCash(follow.getFollowingMemberId(), commission);
			assetService.createAssetHistory(follow, AssetTradeType.FOLLOWER , endingCashByCommission); // 수수료
		}

		/* [API async] 정산 알림 */
		sendSettlementNotification(memberService.getSimpleMember(follow.getFollowerMemberId()).nickname(), follow);
	}

	private void sendSettlementNotification(String nickname, Follow follow) {
		StringBuilder message = new StringBuilder();
		message.append(nickname).append("님이 투자금액 ")
			.append(String.format("%,d", follow.getInvestment())).append("원을 정산하셨습니다. ")
			.append(String.format("%,d", follow.getCommission())).append("원의 수수료를 받았습니다.");

		notificationService.sendNotification(follow.getFollowingMemberId(), NotificationType.SETTLE,
			message.toString());
	}

	private static long getCommission(long proceed, Long investment) {
		return Math.max((long)multiple(proceed - investment, FOLLOW_FEE, CASH_SCALE), 0);
	}

	private static double getReturnRate(long proceed, Long investment) {
		return multiple(PERCENT,
			divide(proceed - investment, investment, NORMAL_SCALE), RETURN_RATE_SCALE);
	}

	private Follow getFollow(Long followId) {
		return followRepository.findById(followId).orElseThrow(() -> new FollowException(FOLLOW_NOT_FOUND));
	}

	public FollowingListResponse readFollowingList(Long memberId, Long lastFollowId) {
		// 유저의 팔로우 자산 목록들
		SliceFollowingInfo sliceFollowingInfo = followRepository.findFollowingInfoListByMemberId(memberId,
			lastFollowId, PAGE_SIZE);

		Map<Long, SimpleMember> simpleMembers = memberService.getSimpleMember(
			sliceFollowingInfo.followingInfoList()
				.stream()
				.map(FollowingInfo::followingId)
				.toList());

		return FollowingListResponse.builder()
			.followings(sliceFollowingInfo.followingInfoList()
				.stream()
				.map(followingInfo ->
					FollowingResponse.getFollowingResponse(followingInfo,
						simpleMembers.get(followingInfo.followingId())))
				.toList())
			.last(sliceFollowingInfo.last())
			.build();
	}

	public FollowerListResponse readFollowerList(Long memberId, String settled, Long lastFollowId) {
		FollowerList followerList = followRepository.findFollowerListByMemberIdAndSettleType(memberId, settled,
			lastFollowId, PAGE_SIZE);

		Map<Long, SimpleMember> simpleMembers = memberService.getSimpleMember(followerList
			.follows().stream()
			.map(Follow::getFollowerMemberId)
			.toList());

		return new FollowerListResponse(followerList.last(),
			followerList.follows().stream()
				.map(follow -> FollowerResponse.getFollowerResponse(follow,
					simpleMembers.get(follow.getFollowerMemberId())))
				.toList());
	}

	public List<FollowTradeDto> getFollowTrades(Pageable pageable, Long followId) {
		Follow follow = followRepository.getReferenceById(followId);

		return followTradeRepository.findByFollow(pageable, follow)
			.stream()
			.map(FollowTradeDto::fromEntity)
			.toList();
	}

	public InvestmentsResponse getInvestments(Long memberId) {
		return new InvestmentsResponse(getTotalFollowInvestment(memberId));
	}

	public List<FollowerInfoResponse> getFollowerInfos(Long followingId) {
		return followRepository.findFollowerInfosByFollowingId(followingId);
	}

	@Transactional
	public void updateFollower(Long followingId, FollowerProfitRequest followerProfitRequest) {
		followRepository.updateFollower(followingId, followerProfitRequest.followerId(), followerProfitRequest.cash());
	}

	public FollowStatusResponse getFollowStatus(Long loginMemberId, Long targetMemberId) {
		return FollowStatusResponse.builder()
			.followed(followRepository.isFollowedByMemberId(loginMemberId, targetMemberId))
			.build();
	}

	public FollowAssetResponse getFollowAsset(Long memberId) {
		return FollowAssetResponse.builder()
			.followingCash(followRepository.getFollowingCashByMemberId(memberId))
			.followerCash(followRepository.getFollowerCashByMemberId(memberId))
			.build();
	}

	@Transactional
	public void modifyFollowingAndFollower(Long memberId) {
		// 팔로잉 : 내가 팔로우하는 사람들 정산
		List<Follow> followingList = followRepository.findAllByFollowerMemberIdAndSettledFalse(memberId);
		for (Follow following : followingList) {
			deleteFollow(following.getId());
		}


		// 팔로워 : 나를 팔로우하는 사람들 정산
		List<Follow> followerList = followRepository.findAllByFollowingMemberIdAndSettledFalse(memberId);
		for (Follow follower : followerList) {
			deleteFollow(follower.getId());
		}
	}

	public InvestmentsResponse getFollowingInvestment(Long memberId) {
		return new InvestmentsResponse(followRepository.findAllByFollowerMemberIdAndSettledFalse(memberId).stream()
			.map(Follow::getInvestment)
			.reduce(0L, Long::sum));
	}
}
