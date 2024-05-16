package com.found_404.funco.asset.service;

import static com.found_404.funco.asset.domain.type.PeriodType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.found_404.funco.asset.domain.AssetHistory;
import com.found_404.funco.asset.domain.repository.AssetHistoryRepository;
import com.found_404.funco.asset.domain.repository.QueryDslAssetHistoryRepository;
import com.found_404.funco.asset.domain.type.TradeType;
import com.found_404.funco.asset.domain.type.AssetType;
import com.found_404.funco.asset.domain.type.PeriodType;
import com.found_404.funco.asset.dto.response.AssetHistoryResponse;
import com.found_404.funco.asset.dto.response.CryptoResponse;
import com.found_404.funco.asset.dto.response.TotalAssetResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetService {

	private final QueryDslAssetHistoryRepository queryDslAssetHistoryRepository;
	private final AssetHistoryRepository assetHistoryRepository;

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

	/*
	* 자산변동내역 V2
	* 필터링 조건을 줘서, 조건에 맞는 데이터들을 담아서 보내주기
	*/
	// 다음 타입들을 AssetHistoryResponse로 추상화하여 넘겨줌
	// CoinHistoryResponse, FollowHistoryResponse, PorfolioHistoryResponse
	public List<? extends AssetHistoryResponse> getMemberHistory(Long memberId, PeriodType period, AssetType asset, TradeType tradeType) {
		// 기간, 자산, 거래 타입
		// 자산 -> 거래 타입 -> 기간
			// 자산 : 임의로 COIN, FOLLOWER, FOLLOWING, PORTFOLIO
			// 거래 타입 : 같이 보내줌
			// 기간 : 자산에 상관 없이 동일

		// 기간은 지금으로부터 정해져 있는 만큼으로 설정
		// switch-case 문으로 between에 들어갈 날짜 설정
		LocalDateTime endDateTime = LocalDateTime.now();
		LocalDate endDate = LocalDate.now();

		LocalDateTime startDateTime = switch(period.name()){
			case "DAY" -> endDate.atStartOfDay();
			case "WEEK" -> endDate.minusWeeks(WEEK.number).atStartOfDay();
			case "ONEMONTH" -> endDate.minusMonths(ONEMONTH.number).atStartOfDay();
			case "THREEMONTH" -> endDate.minusMonths(THREEMONTH.number).atStartOfDay();
			case "SIXMONTH" -> endDate.minusMonths(SIXMONTH.number).atStartOfDay();
			default -> null;
		};

		// asset별 값 전달하기
		return switch(asset.name()){
			case "COIN" -> queryDslAssetHistoryRepository.findCoinHistory(memberId, startDateTime, endDateTime, tradeType);
			case "FOLLOW" -> queryDslAssetHistoryRepository.findFollowHistory(memberId, startDateTime, endDateTime, tradeType);
			case "PORTFOLIO" -> queryDslAssetHistoryRepository.findPortfolioHistory(memberId, startDateTime, endDateTime, tradeType);
			default -> Collections.emptyList();
		};
	}

	@Transactional
	public void initializeMemberCash(Long memberId) {

		// 개선안 : Member의 초기화 날짜를 받아서 비교해준다.
		// 하루(24시간)전이라면 throws 날짜 에러
		//LocalDateTime memberInitCashDate = member.getInitCashDate();
		LocalDateTime now = LocalDateTime.now();
		// if(Objects.nonNull(memberInitCashDate) && now.isBefore(memberInitCashDate.plusHours(24))){
		// 	throw new AssetException(AssetErrorCode.INIT_NOT_ALLOWED);
		// }

		// 강제 정산
		//settleCoinAndFollow(member);

		// 원 초기화 날짜 업데이트
		// member.updateInitCashDate(now);
		// member.updateInitCash();
	}


	@Transactional
	protected void settleCoinAndFollow(Long memberId) {
		// 강제 정산 로직
		// 보유 코인 : 불러와서 삭제하면서 팔로우 거래 처리
		// 팔로우 : 나를 팔로우하는 사람들 정산
		// List<HoldingCoin> memberHoldingCoinList = holdingCoinRepository.findHoldingCoinByMember(member);
		// holdingCoinRepository.deleteAll(memberHoldingCoinList);
		//
		// // 팔로잉 : 내가 팔로우하는 사람들 정산
		// List<Follow> followingList = followRepository.findAllByFollowerAndSettledFalse(member);
		// for(Follow following : followingList){
		// 	followService.deleteFollow(following.getId());
		// }
		//
		// // 팔로워 : 나를 팔로우하는 사람들 정산
		// List<Follow> followerList = followRepository.findAllByFollowingAndSettledFalse(member);
		// for(Follow follower : followerList){
		// 	followService.deleteFollow(follower.getId());
		// }

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
		Long investment, Double returnRate, Long commission, Long settlement, LocalDateTime followDate, Long beginningCash, Long endingCash) {

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
