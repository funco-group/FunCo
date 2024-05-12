package com.found_404.funco.trade.service;

import static com.found_404.funco.global.util.DecimalCalculator.*;
import static com.found_404.funco.global.util.DecimalCalculator.ScaleType.*;
import static com.found_404.funco.trade.exception.TradeErrorCode.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.found_404.funco.global.util.CommissionUtil;
import com.found_404.funco.trade.client.MemberServiceClient;
import feign.FeignException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.trade.cryptoPrice.CryptoPrice;
import com.found_404.funco.trade.domain.HoldingCoin;
import com.found_404.funco.trade.domain.OpenTrade;
import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.domain.repository.HoldingCoinRepository;
import com.found_404.funco.trade.domain.repository.OpenTradeRepository;
import com.found_404.funco.trade.domain.repository.TradeRepository;
import com.found_404.funco.trade.domain.type.TradeType;
import com.found_404.funco.trade.dto.OpenTradeDto;
import com.found_404.funco.trade.dto.OtherTradeDto;
import com.found_404.funco.trade.dto.Ticker;
import com.found_404.funco.trade.dto.TradeDto;
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
	private final MemberServiceClient memberService;

	private final CryptoPrice cryptoPrice;
	//private final FollowTradeService followTradeService;

	private long getPriceByTicker(String ticker) {
		return cryptoPrice.getTickerPrice(ticker);
	}

	@Transactional
	public MarketTradeResponse marketBuying(Long memberId, String ticker, long orderCash) {
		// 시세 가져오기
		long currentPrice = getPriceByTicker(ticker);

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

		// 팔로우 연동
		//followTradeService.followTrade(trade);

		// orderCash <= 자산 check, member 원화 감소
		updateMemberCash(memberId, -orderCash);

		return MarketTradeResponse.builder()
			.ticker(trade.getTicker())
			.price(trade.getPrice())
			.volume(trade.getVolume())
			.build();
	}

	@Transactional
	public MarketTradeResponse marketSelling(Long memberId, String ticker, double volume) {
		// 시세 가져오기
		long currentPrice = getPriceByTicker(ticker);

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

		// 팔로우 연동
		//followTradeService.followTrade(trade);

		// member 원화 증가
		updateMemberCash(memberId, CommissionUtil.getCashWithoutCommission(orderCash));

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
				.map(Ticker::getTicker)
				.collect(Collectors.toList()))
			.build();
	}

	@Transactional(readOnly = true)
	public List<TradeDto> getOrders(Long memberId, String ticker, Pageable pageable) {
		return tradeRepository.findMyTradeHistoryByTicker(memberId, ticker, pageable)
			.stream()
			.map(TradeDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<OtherTradeDto> getOtherOrders(Long memberId, Pageable pageable) {
		return tradeRepository.findMyTradeHistoryByTicker(memberId, null, pageable)
				.stream()
				.map(OtherTradeDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
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
			updateMemberCash(memberId, openTrade.getOrderCash());
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

	private void updateMemberCash(Long memberId, Long cash) {
		try {
			memberService.updateCash(memberId, cash);
		} catch (FeignException e) {
			log.error("member client error : {}", e.getMessage());
			throw new TradeException(INSUFFICIENT_ASSET);
		}
	}

	@Transactional
	public void limitBuying(Long memberId, String ticker, Long price, Double volume) {
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

		cryptoPrice.addTrade(openTrade.getTicker(), openTrade.getId(), openTrade.getTradeType(), openTrade.getPrice());

		// 돈 확인 및 감소
		updateMemberCash(memberId, -orderCash);
	}

	@Transactional
	public void limitSelling(Long memberId, String ticker, Long price, Double volume) {
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
		cryptoPrice.addTrade(openTrade.getTicker(), openTrade.getId(), openTrade.getTradeType(), openTrade.getPrice());
	}

	@Transactional
	public void decreaseHoldingCoin(HoldingCoin holdingCoin, Double volume) {
		holdingCoin.decreaseVolume(volume);
		if (holdingCoin.getVolume() <= 0) {
			holdingCoinRepository.delete(holdingCoin);
		}
	}
}
