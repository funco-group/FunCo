package com.found_404.funco.trade.domain.repository.impl;

import static com.found_404.funco.trade.domain.QHoldingCoin.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.found_404.funco.trade.domain.repository.QueryDslHoldingCoinRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class QueryDslHoldingCoinRepositoryImpl implements QueryDslHoldingCoinRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<String> findHoldingCoin() {
		return jpaQueryFactory.select(
				holdingCoin.ticker
			)
			.from(holdingCoin)
			.distinct()
			.fetch();
	}
}
