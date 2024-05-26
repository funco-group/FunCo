package com.found_404.funco.trade.service;

import static com.found_404.funco.global.util.DecimalCalculator.ScaleType.*;
import static com.found_404.funco.global.util.DecimalCalculator.*;
import static com.found_404.funco.trade.exception.TradeErrorCode.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.found_404.funco.crypto.LiveTradeProcessor;
import com.found_404.funco.feignClient.service.AssetService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.crypto.cryptoPrice.CryptoPrice;
import com.found_404.funco.feignClient.dto.ActiveFutureInfo;
import com.found_404.funco.feignClient.dto.HoldingCoinInfo;
import com.found_404.funco.feignClient.service.FollowService;
import com.found_404.funco.feignClient.service.MemberService;
import com.found_404.funco.global.util.CommissionUtil;
import com.found_404.funco.trade.domain.HoldingCoin;
import com.found_404.funco.trade.domain.OpenTrade;
import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.domain.repository.ActiveFutureRepository;
import com.found_404.funco.trade.domain.repository.HoldingCoinRepository;
import com.found_404.funco.trade.domain.repository.OpenTradeRepository;
import com.found_404.funco.trade.domain.repository.TradeRepository;
import com.found_404.funco.trade.domain.type.TradeType;
import com.found_404.funco.trade.dto.OpenTradeDto;
import com.found_404.funco.trade.dto.OtherTradeDto;
import com.found_404.funco.trade.dto.RecentTradedCoin;
import com.found_404.funco.trade.dto.TradeDto;
import com.found_404.funco.trade.dto.response.CoinValuation;
import com.found_404.funco.trade.dto.response.CoinValuationResponse;
import com.found_404.funco.trade.dto.response.HoldingCoinResponse;
import com.found_404.funco.trade.dto.response.HoldingCoinsResponse;
import com.found_404.funco.trade.dto.response.MarketTradeResponse;
import com.found_404.funco.trade.exception.TradeException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradeService {

	private final TradeRepository tradeRepository;
	private final HoldingCoinRepository holdingCoinRepository;
	private final OpenTradeRepository openTradeRepository;
	private final ActiveFutureRepository activeFutureRepository;

	private final CryptoPrice cryptoPrice;
	private final LiveTradeProcessor liveTradeProcessor;

	private final MemberService memberService;
	private final FollowService followService;
	private final AssetService assetService;

	private double getPriceByTicker(String ticker) {
		return cryptoPrice.getTickerPrice(ticker);
	}

	@Transactional
	public MarketTradeResponse marketBuying(Long memberId, String ticker, long orderCash) {
		// 시세 가져오기
		double currentPrice = getPriceByTicker(ticker);

		// 구매 개수
		double volume = divide(orderCash, currentPrice, VOLUME_SCALE);

		// 코인 있으면 더하기 없으면 추가
		Optional<HoldingCoin> holdingCoin = holdingCoinRepository.findByMemberIdAndTicker(memberId, ticker);
		if (holdingCoin.isPresent()) {
			holdingCoin.get().increaseVolume(volume, currentPrice);
		} else {
			holdingCoinRepository.save(HoldingCoin.builder()
				.ticker(ticker)
				.memberId(memberId)
				.volume(volume)
				.averagePrice(currentPrice)
				.build());
		}

		// 거래 데이터 저장
		Trade trade = Trade.builder()
			.ticker(ticker)
			.tradeType(TradeType.BUY)
			.memberId(memberId)
			.orderCash(orderCash)
			.price(currentPrice)
			.volume(volume)
			.build();

		tradeRepository.save(trade);

		// orderCash <= 자산 check, member 원화 감소
		Long endingCash = memberService.updateMemberCash(memberId, -orderCash);

		// 팔로우 연동
		followService.createFollowTrade(trade);

		// [API] 통합 자산 변동내역
		assetService.createAssetHistory(trade, endingCash);

		return MarketTradeResponse.builder()
			.ticker(trade.getTicker())
			.price(trade.getPrice())
			.volume(trade.getVolume())
			.build();
	}

	@Transactional
	public MarketTradeResponse marketSelling(Long memberId, String ticker, double volume) {
		// 시세 가져오기
		double currentPrice = getPriceByTicker(ticker);

		// 수수료 제외한 잔액 증가
		long orderCash = (long)(multiple(currentPrice, volume, NORMAL_SCALE));

		// 코인 감소
		HoldingCoin holdingCoin = holdingCoinRepository.findByMemberIdAndTicker(memberId, ticker)
			.orElseThrow(() -> new TradeException(INSUFFICIENT_ASSET));

		decreaseHoldingCoin(holdingCoin, volume);

		// 데이터 저장
		Trade trade = Trade.builder()
			.ticker(ticker)
			.tradeType(TradeType.SELL)
			.memberId(memberId)
			.orderCash(orderCash)
			.price(currentPrice)
			.volume(volume)
			.build();

		tradeRepository.save(trade);

		// [API UPDATE] member 원화 증가
		Long endingCash = memberService.updateMemberCash(memberId, CommissionUtil.getCashWithoutCommission(orderCash));

		// [API UPDATE] 팔로우 연동
		followService.createFollowTrade(trade);

		// [API] 통합 자산 변동내역
		assetService.createAssetHistory(trade, endingCash);

		return MarketTradeResponse.builder()
			.ticker(trade.getTicker())
			.price(trade.getPrice())
			.volume(trade.getVolume())
			.build();
	}

	public HoldingCoinsResponse getHoldingCoins(Long memberId) {
		return HoldingCoinsResponse.builder()
			.holdingCoins(holdingCoinRepository
				.findByMemberId(memberId)
				.stream()
				.map(HoldingCoin::getTicker)
				.collect(Collectors.toList()))
			.build();
	}

	public List<TradeDto> getOrders(Long memberId, String ticker, Pageable pageable) {
		return tradeRepository.findMyTradeHistoryByTicker(memberId, ticker, pageable)
			.stream()
			.map(TradeDto::fromEntity)
			.collect(Collectors.toList());
	}

	public List<OtherTradeDto> getOtherOrders(Long memberId, Pageable pageable) {
		return tradeRepository.findMyTradeHistoryByTicker(memberId, null, pageable)
			.stream()
			.map(OtherTradeDto::fromEntity)
			.collect(Collectors.toList());
	}

	public List<OpenTradeDto> getOpenOrders(Long memberId, String ticker, Pageable pageable) {
		// 멤버 아이디, 코인 유무, id 역순,
		return openTradeRepository.findMyOpenTrade(memberId, ticker, pageable)
			.stream()
			.map(OpenTradeDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteOpenTrade(Long memberId, Long openTradeId) {
		OpenTrade openTrade = openTradeRepository.findById(openTradeId)
			.orElseThrow(() -> new TradeException(NOT_FOUND_TRADE));

		if (!openTrade.getMemberId().equals(memberId)) {
			throw new TradeException(TRADE_UNAUTHORIZED);
		}
		openTradeRepository.delete(openTrade);

		// 돈 또는 코인 회수
		if (openTrade.getTradeType().equals(TradeType.BUY)) {
			memberService.updateMemberCash(memberId, openTrade.getOrderCash());
		} else {
			Optional<HoldingCoin> optionalHoldingCoin = holdingCoinRepository.findByMemberIdAndTicker(
				openTrade.getMemberId(), openTrade.getTicker());

			if (optionalHoldingCoin.isEmpty()) {
				holdingCoinRepository.save(HoldingCoin.builder()
					.ticker(openTrade.getTicker())
					.volume(openTrade.getVolume())
					.memberId(openTrade.getMemberId())
					.averagePrice(openTrade.getBuyPrice())
					.build());
			} else {
				optionalHoldingCoin.get().recoverVolume(openTrade.getVolume(), openTrade.getBuyPrice());
			}
		}
	}

	@Transactional
	public void limitBuying(Long memberId, String ticker, Double price, Double volume) {
		long orderCash = (long)(price * volume);

		// 미체결 거래 등록
		OpenTrade openTrade = openTradeRepository.save(OpenTrade.builder()
			.ticker(ticker)
			.tradeType(TradeType.BUY)
			.memberId(memberId)
			.orderCash(orderCash)
			.price(price)
			.volume(volume)
			.build());

		liveTradeProcessor.addTrade(openTrade.getTicker(), openTrade.getId(), openTrade.getTradeType(), openTrade.getPrice());

		// [API UPDATE] 돈 확인 및 감소
		memberService.updateMemberCash(memberId, -orderCash);
	}

	@Transactional
	public void limitSelling(Long memberId, String ticker, Double price, Double volume) {
		// 코인 확인 및 팔려고 등록한 만큼 빼기
		Optional<HoldingCoin> optionalHoldingCoin = holdingCoinRepository.findByMemberIdAndTicker(memberId, ticker);
		if (optionalHoldingCoin.isEmpty() || optionalHoldingCoin.get().getVolume() < volume) {
			throw new TradeException(INSUFFICIENT_ASSET);
		} else {
			decreaseHoldingCoin(optionalHoldingCoin.get(), volume);
		}

		// 미체결 거래 생성
		OpenTrade openTrade = openTradeRepository.save(OpenTrade.builder()
			.ticker(ticker)
			.tradeType(TradeType.SELL)
			.memberId(memberId)
			.orderCash((long)multiple(volume, price, CASH_SCALE))
			.price(price)
			.volume(volume)
			.buyPrice(optionalHoldingCoin.get().getAveragePrice())
			.build());

		// 미체결 거래 등록
		liveTradeProcessor.addTrade(openTrade.getTicker(), openTrade.getId(), openTrade.getTradeType(), openTrade.getPrice());
	}

	@Transactional
	public void decreaseHoldingCoin(HoldingCoin holdingCoin, Double volume) {
		holdingCoin.decreaseVolume(volume);
		if (holdingCoin.getVolume() <= 0) {
			holdingCoinRepository.delete(holdingCoin);
		}
	}

	public HoldingCoinResponse getHoldingCoin(Long memberId, String ticker) {
		Optional<HoldingCoin> holdingCoin = holdingCoinRepository.findByMemberIdAndTicker(memberId, ticker);

		return HoldingCoinResponse.builder()
			.ticker(ticker)
			.volume(holdingCoin.isPresent() ? holdingCoin.get().getVolume() : 0D)
			.build();
	}

	// 미체결 + 코인총합
	@Transactional(readOnly = true)
	public CoinValuationResponse getCoinValuations(Long memberId) {
		List<HoldingCoin> holdingCoins = holdingCoinRepository.findByMemberId(memberId);

		Map<String, Double> tickerPriceMap = cryptoPrice.getTickerPriceMap(holdingCoins.stream()
			.map(HoldingCoin::getTicker)
			.collect(Collectors.toList()));

		List<CoinValuation> coinValuations = holdingCoins.stream()
			.map(holdingCoin -> CoinValuation.builder()
				.valuation((long)multiple(tickerPriceMap.get(holdingCoin.getTicker()), holdingCoin.getVolume(),
					CASH_SCALE))
				.ticker(holdingCoin.getTicker())
				.price(tickerPriceMap.get(holdingCoin.getTicker()))
				.build())
			.toList();

		long totalCoinValues = coinValuations
			.stream()
			.map(CoinValuation::valuation)
			.reduce(0L, Long::sum);

		long totalOpenTradeCash = openTradeRepository.findAllByMemberId(memberId)
			.stream()
			.map(OpenTrade::getOrderCash)
			.reduce(0L, Long::sum);

		return CoinValuationResponse.builder()
			.coinValuations(coinValuations)
			.totalTradeAsset(totalCoinValues + totalOpenTradeCash)
			.build();
	}

	public List<HoldingCoinResponse> getHoldingCoinInfos(Long memberId) {
		List<HoldingCoin> holdingCoins = holdingCoinRepository.findByMemberId(memberId);

		return holdingCoins.stream()
			.map(holdingCoin -> HoldingCoinResponse.builder()
				.ticker(holdingCoin.getTicker())
				.volume(holdingCoin.getVolume())
				.build())
			.collect(Collectors.toList());
	}

	public List<RecentTradedCoin> getRecentTradedCoins(Long memberId) {
		return tradeRepository.findRecentTradedCoinByMemberId(memberId);
	}

	@Transactional
	public void removeAsset(Long memberId) {
		// 보유 중인 코인 조회
		List<HoldingCoin> holdingCoins = holdingCoinRepository.findByMemberId(memberId);

		// 모든 보유 중인 코인 삭제
		holdingCoinRepository.deleteAll(holdingCoins);

		// 지정가 거래 조회
		List<OpenTrade> openTrades = openTradeRepository.findAllByMemberId(memberId);

		// 모든 지정가 거래 삭제
		openTradeRepository.deleteAll(openTrades);

		/* 선물 거래 삭제 로직 */
		activeFutureRepository.deleteAll(activeFutureRepository.findByMemberId(memberId));
	}

	public List<HoldingCoinInfo> getAssetHoldingCoin(Long memberId) {
		return holdingCoinRepository.findByMemberId(memberId).stream()
			.map(holdingCoin -> HoldingCoinInfo.builder()
				.ticker(holdingCoin.getTicker())
				.volume(holdingCoin.getVolume())
				.averagePrice(holdingCoin.getAveragePrice())
				.build())
			.toList();
	}

	public List<ActiveFutureInfo> getAssetFuture(Long memberId) {
		return activeFutureRepository.findByMemberId(memberId).stream()
			.map(activeFuture -> ActiveFutureInfo.builder()
				.ticker(activeFuture.getTicker())
				.tradeType(activeFuture.getTradeType())
				.orderCash(activeFuture.getOrderCash())
				.price(activeFuture.getPrice())
				.leverage(activeFuture.getLeverage())
				.build())
			.toList();
	}

}
