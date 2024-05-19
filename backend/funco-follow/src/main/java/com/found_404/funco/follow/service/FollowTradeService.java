package com.found_404.funco.follow.service;

import static com.found_404.funco.follow.domain.type.TradeType.*;
import static com.found_404.funco.global.util.DecimalCalculator.*;
import static com.found_404.funco.global.util.DecimalCalculator.ScaleType.*;

import java.util.List;
import java.util.Optional;

import com.found_404.funco.feignClient.service.MemberService;
import com.found_404.funco.feignClient.service.TradeService;
import com.found_404.funco.follow.domain.Follow;
import com.found_404.funco.follow.domain.FollowTrade;
import com.found_404.funco.follow.domain.FollowingCoin;
import com.found_404.funco.follow.domain.repository.FollowRepository;
import com.found_404.funco.follow.domain.repository.FollowTradeRepository;
import com.found_404.funco.follow.domain.repository.FollowingCoinRepository;
import com.found_404.funco.follow.dto.Trade;
import com.found_404.funco.follow.dto.request.FuturesTrade;
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

    private final MemberService memberService;
    private final TradeService tradeService;

    @Async
    public void followTrade(Trade trade) {
        List<Follow> followerList = followRepository.findAllByFollowingMemberIdAndSettledFalse(trade.memberId());

        followTradeRepository.saveAll(followerList.stream()
                .map(follow -> getFollowTrade(trade, follow))
                .toList());
    }

    public FollowTrade getFollowTrade(Trade trade, Follow follow) {
        double volume;
        long orderCash;

        if (trade.tradeType().equals(BUY)) {
            // [API SELECT] 부모가 현금에서 얼마를 썼냐 비율, (팔로우, 미체결 현금,,필요)
            long prevCash = memberService.getMemberCash(follow.getFollowingMemberId()) + trade.orderCash();

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
            // [API SELECT] 코인에서 얼마를 팔았냐 비율
            double prevVolume = trade.volume() + tradeService.getHoldingCoinVolume(follow.getFollowingMemberId(), trade.ticker());
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

        log.info("member: {} -> follower: {}, {}가 {}원에 {}만큼 {}원어치 거래 체결.", follow.getFollowingMemberId(), follow.getFollowerMemberId(),
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

    public void followTrade(List<Trade> trades) {
        trades.forEach(this::followTrade);
    }

    public void followTradeByFutures(FuturesTrade futures) {
        List<Follow> followerList = followRepository.findAllByFollowingMemberIdAndSettledFalse(futures.memberId());

        followTradeRepository.saveAll(followerList.stream()
                .map(follow -> getFollowTrade(futures, follow))
                .toList());
    }

    @Transactional
    public FollowTrade getFollowTrade(FuturesTrade futures, Follow follow) {
        long prevCash = memberService.getMemberCash(follow.getFollowingMemberId()) - futures.settlement(); // 정산 전 자산

        double ratio = divide(futures.settlement(), prevCash, NORMAL_SCALE); // 얼마 비율을 벌거나 잃었는가

        // 팔로우의 자산 증가 또는 감소
        long followerSettlement = (long) multiple(follow.getCash(), ratio, CASH_SCALE);
        follow.updateCash(followerSettlement);

        log.info("부모{}에 의해 자식: {} 거래\n => {} 선물 {} 거래 정산 결과:{}", follow.getFollowingMemberId(), follow.getFollowerMemberId(),
                futures.ticker(), futures.tradeType(), followerSettlement);

        return FollowTrade.builder()
                .follow(follow)
                .tradeType(futures.tradeType())
                .price(futures.price())
                .ticker(futures.ticker())
                .volume((double) followerSettlement) // 세틀
                .orderCash(futures.orderCash())
                .build();
    }
}
