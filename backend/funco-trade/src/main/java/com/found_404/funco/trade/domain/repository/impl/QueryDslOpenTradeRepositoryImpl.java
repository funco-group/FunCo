package com.found_404.funco.trade.domain.repository.impl;

import static com.found_404.funco.trade.domain.QOpenTrade.*;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.found_404.funco.trade.domain.OpenTrade;
import com.found_404.funco.trade.domain.repository.QueryDslOpenTradeRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class QueryDslOpenTradeRepositoryImpl implements QueryDslOpenTradeRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OpenTrade> findMyOpenTrade(Long memberId, String ticker, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(openTrade)
                .where(openTrade.memberId.eq(memberId),
                        filterTicker(ticker))
                .orderBy(openTrade.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    private Predicate filterTicker(String ticker) {
        return Objects.nonNull(ticker) ? openTrade.ticker.eq(ticker) : null;
    }

}
