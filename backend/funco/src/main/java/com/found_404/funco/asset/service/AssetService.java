package com.found_404.funco.asset.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.found_404.funco.asset.domain.AssetHistory;
import com.found_404.funco.asset.domain.repository.AssetHistoryRepository;
import com.found_404.funco.asset.domain.repository.QueryDslAssetHistoryRepository;
import com.found_404.funco.asset.domain.type.AssetTradeType;
import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.asset.dto.ActiveFutureInfo;
import com.found_404.funco.asset.dto.HoldingCoinInfo;
import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
import com.found_404.funco.asset.dto.response.CashResponse;
import com.found_404.funco.asset.dto.response.CoinHistoryResponse;
import com.found_404.funco.asset.dto.response.CryptoResponse;
import com.found_404.funco.asset.dto.response.HistoryResponse;
import com.found_404.funco.asset.dto.response.TotalAssetResponse;
import com.found_404.funco.asset.exception.AssetErrorCode;
import com.found_404.funco.asset.exception.AssetException;
import com.found_404.funco.follow.domain.Follow;
import com.found_404.funco.follow.domain.repository.FollowRepository;
import com.found_404.funco.follow.service.FollowService;
import com.found_404.funco.member.domain.Member;
import com.found_404.funco.member.domain.repository.MemberRepository;
import com.found_404.funco.member.exception.MemberErrorCode;
import com.found_404.funco.member.exception.MemberException;
import com.found_404.funco.trade.domain.ActiveFuture;
import com.found_404.funco.trade.domain.HoldingCoin;
import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.domain.repository.ActiveFutureRepository;
import com.found_404.funco.trade.domain.repository.HoldingCoinRepository;
import com.found_404.funco.trade.domain.repository.TradeRepository;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetService {

	private final MemberRepository memberRepository;
	private final FollowRepository followRepository;
	private final HoldingCoinRepository holdingCoinRepository;
	private final TradeRepository tradeRepository;
	private final ActiveFutureRepository activeFutureRepository;
	private final FollowService followService;
	private final QueryDslAssetHistoryRepository queryDslAssetHistoryRepository;
	private final AssetHistoryRepository assetHistoryRepository;

	public CashResponse getMemberCash(Member member) {
		return new CashResponse(member.getCash());
	}


	/*
	* v1 대비 v2 추가 사항
	* 선물 거래 내역 추가
	* 이외는 동일
	* */
	public TotalAssetResponse getMemberTotalAsset(Long memberId) {

		Member member = findByMemberId(memberId);

		// 가용 현금
		Long memberCash = member.getCash();

		// 해당 멤버가 팔로우 중인 총 초기 투자 금액
		List<Follow> follows = followRepository.findAllByFollowerAndSettledFalse(member);
		Long memberFollwingInvestment = follows.stream()
			.mapToLong(Follow::getInvestment)
			.sum();

		// 해당 멤버가 보유 중인 코인
		// HoldingCoinInfo에 담아서 응답으로 컨트롤러에 넘겨줌
		List<HoldingCoin> holdingCoins = holdingCoinRepository.findHoldingCoinByMember(member);
		List<HoldingCoinInfo> memberHoldingCoinInfos = holdingCoins.stream()
			.map(holdingCoin -> new HoldingCoinInfo(
				holdingCoin.getTicker(),
				holdingCoin.getVolume(),
				holdingCoin.getAveragePrice()
			))
			.toList();

		// 해당 멤버가 보유 중인 선물 거래
		List<ActiveFuture> activeFutures = activeFutureRepository.findActiveFutureByMember(member);
		List<ActiveFutureInfo> memberActiveFutureInfos = activeFutures.stream()
			.map(activeFuture -> new ActiveFutureInfo(
				activeFuture.getTicker(),
				activeFuture.getTradeType(),
				activeFuture.getOrderCash(),
				activeFuture.getPrice(),
				activeFuture.getLeverage()
			))
			.toList();

		return TotalAssetResponse.builder()
			.cash(memberCash)
			.followingInvestment(memberFollwingInvestment)
			.holdingCoinInfos(memberHoldingCoinInfos)
			.activeFutureInfos(memberActiveFutureInfos)
			.build();
	}

	public CryptoResponse getCrypto(Member member, String ticker) {
		Optional<HoldingCoin> optionalHoldingCoin = holdingCoinRepository.findByMemberAndTicker(member, ticker);
		return new CryptoResponse(optionalHoldingCoin.isPresent() ? optionalHoldingCoin.get().getVolume() : 0);
	}

	public List<HistoryResponse> getMemberHistory(Member member) {

		// HistoryResponse들을 담을 리스트
		List<HistoryResponse> historyResponses = new ArrayList<>();

		// 직접 투자
		List<Trade> trades = tradeRepository.findAllByMember(member);
		trades.forEach(trade -> {
			HistoryResponse response = HistoryResponse.builder()
				.date(trade.getCreatedAt())
				.name(trade.getTicker())
				.assetType(AssetType.COIN)
				.tradeType(trade.getTradeType().toString())
				.volume(trade.getVolume())
				.orderCash(trade.getOrderCash())
				.price(trade.getPrice())
				.build();
			historyResponses.add(response);
		});

		// 팔로우 투자
		// 해당 멤버가(팔로워가) 정산한 팔로우 거래 내역
		List<Follow> followings = followRepository.findAllByFollowerAndSettledTrue(member);

		followings.forEach(following -> {
			HistoryResponse response = HistoryResponse.builder()
				.date(following.getSettleDate())
				.name(following.getFollower().getNickname())
				.assetType(AssetType.FOLLOW)
				.tradeType("FOLLOWING")
				.volume(1d)
				.orderCash(following.getInvestment())
				.settlement(following.getSettlement())
				.build();
			historyResponses.add(response);
		});

		// 해당 멤버를 팔로우 한 사람(팔로잉)이 정산한 거래 내역
		List<Follow> followers = followRepository.findAllByFollowingAndSettledTrue(member);
		followers.forEach(follower -> {
			HistoryResponse response = HistoryResponse.builder()
				.date(follower.getSettleDate())
				.name(follower.getFollower().getNickname())
				.assetType(AssetType.FOLLOW)
				.tradeType("FOLLOWER")
				.volume(1d)
				.orderCash(follower.getInvestment())
				.commission(follower.getCommission())
				.build();
			historyResponses.add(response);
		});

		// DTO가 담긴 리스트를 date 순으로 정렬
		return historyResponses.stream()
			.sorted(Comparator.comparing(HistoryResponse::date).reversed())
			.collect(Collectors.toList());
	}

	/*
	* 자산변동내역 V2
	* 필터링 조건을 줘서, 조건에 맞는 데이터들을 담아서 보내주기
	*/

	// 다음 타입들을 AssetHistoryResponse로 추상화하여 넘겨줌
		// CoinHistoryResponse, FollowHistoryResponse, PorfolioHistoryResponse
	public List<? extends AssetHistoryResponse> getMemberHistoryV2(Long memberId, String period, String asset, String tradeType) {


		// 기간, 자산, 거래 타입
		// 자산 -> 거래 타입 -> 기간
			// 자산 : 임의로 COIN, FOLLOWER, FOLLOWING, PORTFOLIO
			// 거래 타입 : 같이 보내줌
			// 기간 : 자산에 상관 없이 동일

		// 기간은 지금으로부터 정해져 있는 만큼으로 설정
		// switch-case 문으로 between에 들어갈 날짜 설정
		LocalDateTime endDateTime = LocalDateTime.now();
		LocalDate endDate = LocalDate.now();
		LocalDateTime startDateTime = switch(period){
			case "DAY" -> endDate.atStartOfDay();
			case "WEEK" -> endDate.minusWeeks(1).atStartOfDay();
			case "ONEMONTH" -> endDate.minusMonths(1).atStartOfDay();
			case "THREEMONTH" -> endDate.minusMonths(3).atStartOfDay();
			case "SIXMONTH" -> endDate.minusMonths(6).atStartOfDay();
			default -> null;
		};

		// asset별 값 전달하기
		switch(asset){
			case "COIN" -> queryDslAssetHistoryRepository.findCoinHistory(memberId, startDateTime, endDateTime, tradeType);
			case "FOLLOW" -> queryDslAssetHistoryRepository.findFollowHistory(memberId, startDateTime, endDateTime, tradeType);
			case "PORTFOLIO" -> queryDslAssetHistoryRepository.findPortfolioHistory(memberId, startDateTime, endDateTime, tradeType);
		}

		return new ArrayList<>();
	}


	@Transactional
	public void initializeMemberCash(Member member) {

		// 개선안 : Member의 초기화 날짜를 받아서 비교해준다.
		// 하루(24시간)전이라면 throws 날짜 에러
		LocalDateTime memberInitCashDate = member.getInitCashDate();
		LocalDateTime now = LocalDateTime.now();
		if(Objects.nonNull(memberInitCashDate) && now.isBefore(memberInitCashDate.plusHours(24))){
			throw new AssetException(AssetErrorCode.INIT_NOT_ALLOWED);
		}

		// 강제 정산
		settleCoinAndFollow(member);

		// 원 초기화 날짜 업데이트
		member.updateInitCashDate(now);
		member.updateInitCash();


	}


	@Transactional
	protected void settleCoinAndFollow(Member member) {
		// 강제 정산 로직
		// 보유 코인 : 불러와서 삭제하면서 팔로우 거래 처리
		// 팔로우 : 나를 팔로우하는 사람들 정산
		List<HoldingCoin> memberHoldingCoinList = holdingCoinRepository.findHoldingCoinByMember(member);
		holdingCoinRepository.deleteAll(memberHoldingCoinList);

		// 팔로잉 : 내가 팔로우하는 사람들 정산
		List<Follow> followingList = followRepository.findAllByFollowerAndSettledFalse(member);
		for(Follow following : followingList){
			followService.deleteFollow(following.getId());
		}

		// 팔로워 : 나를 팔로우하는 사람들 정산
		List<Follow> followerList = followRepository.findAllByFollowingAndSettledFalse(member);
		for(Follow follower : followerList){
			followService.deleteFollow(follower.getId());
		}

	}

	private Member findByMemberId(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
	}
}
