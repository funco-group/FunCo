package com.found_404.funco.trade.service;

import java.util.List;
import java.util.Optional;

import com.found_404.funco.crypto.cryptoPrice.LoadTrade;
import com.found_404.funco.feignClient.dto.NotificationType;
import com.found_404.funco.feignClient.service.AssetService;
import com.found_404.funco.feignClient.service.FollowService;
import com.found_404.funco.feignClient.service.MemberService;
import com.found_404.funco.feignClient.service.NotificationService;
import com.found_404.funco.global.util.CommissionUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.trade.domain.HoldingCoin;
import com.found_404.funco.trade.domain.OpenTrade;
import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.domain.repository.HoldingCoinRepository;
import com.found_404.funco.trade.domain.repository.OpenTradeRepository;
import com.found_404.funco.trade.domain.repository.TradeRepository;
import com.found_404.funco.trade.domain.type.TradeType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OpenTradeService {
    private final TradeRepository tradeRepository;
    private final HoldingCoinRepository holdingCoinRepository;
    private final OpenTradeRepository openTradeRepository;

    private final FollowService followService;
    private final MemberService memberService;
    private final NotificationService notificationService;
    private final AssetService assetService;

    @Async
    @Transactional
    public void processTrade(List<Long> concludingTradeIds, Double tradePrice) {

        // 거래 처리할 미체결 거래 가져오기
        List<OpenTrade> openTrades = openTradeRepository.findAllByIdIn(concludingTradeIds);

        // 미체결 데이터 삭제
        openTradeRepository.deleteAll(openTrades);

        // 체결 데이터로 전환
        List<Trade> trades = openTrades.stream()
                .map(openTrade -> OpenTrade.toTrade(openTrade, tradePrice))
                .toList();

        // 체결 데이터 저장
        tradeRepository.saveAll(trades);

        // 자산 업데이트
        for (int i = 0; i < trades.size(); i++) {
            processAsset(trades.get(i), Math.abs(openTrades.get(i).getOrderCash() - trades.get(i).getOrderCash()));
        }

        // [API async] 알림
        trades.forEach(trade ->
                notificationService.sendNotification(trade.getMemberId(),
                        trade.getTradeType().equals(TradeType.BUY) ? NotificationType.BUY : NotificationType.SELL, getMessage(trade)));

        // [API update async ] 팔로우 구매
        followService.createFollowTrade(trades);
    }

    private String getMessage(Trade trade) {
        StringBuilder message = new StringBuilder();
        message.append("[").append(trade.getTicker()).append("] ")
                .append(String.format("%,f", trade.getVolume())).append("개 ")
                .append(String.format("%,f", trade.getPrice())).append("원 ")
                .append(trade.getTradeType().getKorean()).append(" 체결 ");
        return message.toString();
    }

    private void processAsset(Trade trade, Long recoverCash) {
        Long endingCash = 0L;
        if (trade.getTradeType().equals(TradeType.BUY)) { // BUY
            Optional<HoldingCoin> optionalHoldingCoin = holdingCoinRepository.findByMemberIdAndTicker(trade.getMemberId(), trade.getTicker());
            HoldingCoin holdingCoin;
            if (optionalHoldingCoin.isPresent()) {
                holdingCoin = optionalHoldingCoin.get();
                holdingCoin.increaseVolume(trade.getVolume(), trade.getPrice());
            } else {
                holdingCoin = HoldingCoin.builder()
                        .memberId(trade.getMemberId())
                        .volume(trade.getVolume())
                        .averagePrice(trade.getPrice())
                        .ticker(trade.getTicker())
                        .build();
            }

            holdingCoinRepository.save(holdingCoin);
            // [API UPDATE] 거래 금액 대비 차액 입금
            endingCash = memberService.updateMemberCash(trade.getMemberId(), recoverCash);
        } else { // SELL
            // [API UPDATE] 자산 증가 + 거래 금액 대비 차액 입금
            endingCash = memberService.updateMemberCash(trade.getMemberId(), CommissionUtil.getCashWithoutCommission(trade.getOrderCash()) - recoverCash);
        }

        // [API] 통합 자산 변동내역
        assetService.createAssetHistory(trade, endingCash);
    }

    public List<LoadTrade> getLoadTrades() {
        return openTradeRepository.findAll()
                .stream().map(LoadTrade::getLoadTrade)
                .toList();
    }

}
