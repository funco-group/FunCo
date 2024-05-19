package com.found_404.funco.trade.service;

import com.found_404.funco.crypto.cryptoPrice.CryptoPrice;
import com.found_404.funco.feignClient.service.AssetService;
import com.found_404.funco.feignClient.service.FollowService;
import com.found_404.funco.feignClient.service.MemberService;
import com.found_404.funco.global.util.CommissionUtil;
import com.found_404.funco.trade.domain.ActiveFuture;
import com.found_404.funco.trade.domain.FutureTrade;
import com.found_404.funco.trade.domain.repository.ActiveFutureRepository;
import com.found_404.funco.trade.domain.repository.FutureTradeRepository;
import com.found_404.funco.trade.domain.type.TradeType;
import com.found_404.funco.trade.dto.FutureTradeDto;
import com.found_404.funco.trade.dto.request.RequestBuyFutures;
import com.found_404.funco.trade.dto.ActiveFutureDto;
import com.found_404.funco.trade.exception.TradeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.found_404.funco.global.util.DecimalCalculator.*;
import static com.found_404.funco.global.util.DecimalCalculator.ScaleType.CASH_SCALE;
import static com.found_404.funco.global.util.DecimalCalculator.ScaleType.NORMAL_SCALE;
import static com.found_404.funco.trade.domain.type.TradeType.SHORT;
import static com.found_404.funco.trade.exception.TradeErrorCode.ALREADY_FUTURES_TRADE;
import static com.found_404.funco.trade.exception.TradeErrorCode.NOT_FOUND_TRADE;

@Service
@Slf4j
@RequiredArgsConstructor
public class FutureService {
    private final ActiveFutureRepository activeFutureRepository;
    private final FutureTradeRepository futureTradeRepository;
    private final CryptoPrice cryptoPrice;

    private final MemberService memberService;
    private final FollowService followService;
    private final AssetService assetService;

    @Transactional
    public void buyFuturesLong(Long memberId, RequestBuyFutures requestBuyFutures) {
        // 이미 선물 거래 중인지 체크, 선물 거래는 코인 당 한번에 1개 가능
        checkExistFutures(memberId, requestBuyFutures);

        ActiveFuture activeFuture = activeFutureRepository.save(getActiveFuture(memberId, TradeType.LONG, requestBuyFutures));

        log.info("[Long] member:{} {} 가격 {} 아래로 청산됩니다.", memberId, activeFuture.getTicker(), activeFuture.getLiquidatedPrice());
        cryptoPrice.addTrade(requestBuyFutures.ticker(), activeFuture.getId(), TradeType.LONG, activeFuture.getLiquidatedPrice());

        // [API UPDATE] 멤버 자산 감소
        memberService.updateMemberCash(memberId, -requestBuyFutures.orderCash());
    }

    private static double getDifference(Double price, Integer leverage) {
        return multiple(price, divide(1, leverage, NORMAL_SCALE), NORMAL_SCALE);
    }

    private void checkExistFutures(Long memberId, RequestBuyFutures requestBuyFutures) {
        activeFutureRepository.findByMemberIdAndTicker(memberId, requestBuyFutures.ticker())
                .ifPresent(activeFuture -> {
                    throw new TradeException(ALREADY_FUTURES_TRADE);
                });
    }

    @Transactional
    public void buyFuturesShort(Long memberId, RequestBuyFutures requestBuyFutures) {
        // 이미 선물 거래 중인지 체크, 선물 거래는 코인 당 한번에 1개 가능
        checkExistFutures(memberId, requestBuyFutures);

        ActiveFuture activeFuture = activeFutureRepository.save(getActiveFuture(memberId, SHORT, requestBuyFutures));

        log.info("[Short] member:{} {} 가격 {} 위로 청산됩니다.", memberId, activeFuture.getTicker(), activeFuture.getLiquidatedPrice());
        cryptoPrice.addTrade(requestBuyFutures.ticker(), activeFuture.getId(), SHORT, activeFuture.getLiquidatedPrice());

        // [API UPDATE] 멤버 자산 감소
        memberService.updateMemberCash(memberId, -requestBuyFutures.orderCash());
    }

    private ActiveFuture getActiveFuture(Long memberId, TradeType tradeType, RequestBuyFutures requestBuyFutures) {
        double tickerPrice = cryptoPrice.getTickerPrice(requestBuyFutures.ticker());

        double difference = getDifference(tickerPrice, requestBuyFutures.leverage());
        if(tradeType == SHORT) {
            difference = -difference;
        }
        double liquidatedPrice = tickerPrice - difference;

        return ActiveFuture.builder()
                .ticker(requestBuyFutures.ticker())
                .tradeType(tradeType)
                .orderCash(requestBuyFutures.orderCash())
                .memberId(memberId)
                .leverage(requestBuyFutures.leverage())
                .price(tickerPrice)
                .liquidatedPrice(liquidatedPrice)
                .build();
    }

    @Transactional
    public void settlement(Long memberId, Long futureId) {
        ActiveFuture activeFuture = activeFutureRepository.findById(futureId)
                .orElseThrow(() -> new TradeException(NOT_FOUND_TRADE));

        double currentPrice = cryptoPrice.getTickerPrice(activeFuture.getTicker());


        // 수익 = (현재시세 - 가격 ) / 진입가격 * 주문금액 * 레버리지
        long settlement = (long) multiple(multiple(divide(minus(currentPrice, activeFuture.getPrice(), NORMAL_SCALE),
                                activeFuture.getPrice(), NORMAL_SCALE),
                        activeFuture.getOrderCash(), NORMAL_SCALE),
                activeFuture.getLeverage(), CASH_SCALE);

        if (activeFuture.getTradeType().equals(SHORT)) {
            settlement = -settlement;
        }

        activeFutureRepository.delete(activeFuture);
        FutureTrade futureTrade = futureTradeRepository.save(FutureTrade.fromActiveFutures(activeFuture, settlement));

        // [API UPDATE] 멤버 자산 증가
        Long endingCash = memberService.updateMemberCash(memberId, CommissionUtil.getCashWithoutCommission(activeFuture.getOrderCash() + settlement));

        // [API UPDATE] 팔로우 연동
        followService.createFollowTrade(futureTrade);

        // [API] 자산 변동내역 전송
        assetService.createAssetHistory(futureTrade, endingCash);
    }

    public ActiveFutureDto getActiveFutures(Long memberId, String ticker) {
        return ActiveFutureDto.fromEntity(activeFutureRepository.findByMemberIdAndTicker(memberId, ticker)
                .orElseThrow(() -> new TradeException(NOT_FOUND_TRADE)));
    }

    public List<FutureTradeDto> getFutures(Long memberId, String ticker) {
        return futureTradeRepository.findAllByMemberIdAndTickerOrderByIdDesc(memberId, ticker)
                .stream()
                .map(FutureTradeDto::fromEntity)
                .toList();
    }

}
