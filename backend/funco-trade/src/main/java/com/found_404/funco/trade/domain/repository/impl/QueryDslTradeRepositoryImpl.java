package com.found_404.funco.trade.domain.repository.impl;

import static com.found_404.funco.trade.domain.QTrade.*;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.domain.repository.QueryDslTradeRepository;
import com.found_404.funco.trade.dto.QRecentTradedCoin;
import com.found_404.funco.trade.dto.RecentTradedCoin;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class QueryDslTradeRepositoryImpl implements QueryDslTradeRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private static final int RECENT_TRADED_COIN_SIZE = 3;

    @Override
    public List<Trade> findMyTradeHistoryByTicker(Long memberId, String ticker, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(trade)
                .where(trade.memberId.eq(memberId),
                        filterTicker(ticker))
                .orderBy(trade.updatedAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public List<RecentTradedCoin> findRecentTradedCoinByMemberId(Long memberId) {
        return jpaQueryFactory
            .select(new QRecentTradedCoin(trade.ticker, trade.createdAt))
            .from(trade)
            .where(trade.memberId.eq(memberId))
            .groupBy(trade.ticker)
            .orderBy(trade.createdAt.desc())
            .limit(RECENT_TRADED_COIN_SIZE)
            .fetch();
    }

    private Predicate filterTicker(String ticker) {
        return Objects.nonNull(ticker) ? trade.ticker.eq(ticker) : null;
    }

}
