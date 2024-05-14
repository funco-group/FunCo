package com.found_404.funco.follow.service;

import static com.found_404.funco.follow.domain.type.TradeType.*;
import static com.found_404.funco.global.util.DecimalCalculator.*;
import static com.found_404.funco.global.util.DecimalCalculator.ScaleType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.found_404.funco.follow.domain.Follow;
import com.found_404.funco.follow.domain.FollowTrade;
import com.found_404.funco.follow.domain.FollowingCoin;
import com.found_404.funco.follow.domain.repository.FollowRepository;
import com.found_404.funco.follow.domain.repository.FollowTradeRepository;
import com.found_404.funco.follow.domain.repository.FollowingCoinRepository;
import com.found_404.funco.follow.dto.Trade;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FollowTradeService {
    private final FollowRepository followRepository;

    private final FollowingCoinRepository followingCoinRepository;
    private final FollowTradeRepository followTradeRepository;

    //private final HoldingCoinRepository holdingCoinRepository;

    @Async
    public void followTrade(Trade trade) {
        List<Follow> followerList = followRepository.findAllByFollowerMemberIdAndSettledFalse(trade.memberId());

        followTradeRepository.saveAll(followerList.stream()
                .map(follow -> getTrade(trade, follow))
                .toList());
    }

    public FollowTrade getTrade(Trade trade, Follow follow) {

        double volume;
        long orderCash;

        if (trade.tradeType().equals(BUY)) {
            // 부모가 현금에서 얼마를 썼냐 비율 => 캡쳐링 필요
            //long prevCash = following.getCash() + trade.orderCash();
            long prevCash = 0;
            double ratio = divide(trade.orderCash(), prevCash, NORMAL_SCALE);

            orderCash = (long) multiple(follow.getCash(), ratio, CASH_SCALE);
            volume = divide(orderCash, trade.price(), VOLUME_SCALE);

            // 돈 쓰기
            follow.decreaseCash(orderCash);

            // 코인 추가
            Optional<FollowingCoin> followerCoin = followingCoinRepository.findByFollowAndTicker(follow, trade.ticker());
            if (followerCoin.isEmpty()) {
                followingCoinRepository.save(FollowingCoin.builder()
                        .ticker(trade.ticker())
                        .averagePrice(trade.price())
                        .volume(volume)
                        .follow(follow)
                        .build());
            } else {
                followerCoin.get().increaseVolume(volume, trade.price());
            }
        } else {
            // 코인에서 얼마를 팔았냐 비율
            //Optional<HoldingCoin> followingCoin = holdingCoinRepository.findByMemberAndTicker(following, trade.ticker());

            //double prevVolume = trade.volume() + (followingCoin.isEmpty() ? 0 : followingCoin.get().getVolume());
            double prevVolume = 0;

            double ratio = divide(trade.volume(), prevVolume, NORMAL_SCALE);

            FollowingCoin followerCoin = followingCoinRepository.findByFollowAndTicker(follow, trade.ticker())
                    .orElseThrow(() -> new RuntimeException("잔액 부족"));

            volume = multiple(followerCoin.getVolume(), ratio, VOLUME_SCALE);
            orderCash = (long) multiple(trade.price(), volume, NORMAL_SCALE);

            // 돈 추가
            follow.increaseCash(orderCash);

            // 코인 감소
            followerCoin.decreaseVolume(volume);
            if (followerCoin.getVolume() <= 0) {
                followingCoinRepository.delete(followerCoin);
            }
        }

        log.info("[{}] member: {} -> follower: {}, {}가 {}원에 {}만큼 {}원어치 거래 체결.", LocalDateTime.now(), follow.getFollowingMemberId(), follow.getFollowerMemberId(),
                trade.ticker(), trade.price(), volume, orderCash);

        return FollowTrade.builder()
                .follow(follow)
                .tradeType(trade.tradeType())
                .price(trade.price())
                .ticker(trade.ticker())
                .volume(volume) // 비율
                .orderCash(orderCash)
                .build();
    }

    @Async
    public void followTrade(List<Trade> trades) {
        trades.forEach(this::followTrade);
    }


}
