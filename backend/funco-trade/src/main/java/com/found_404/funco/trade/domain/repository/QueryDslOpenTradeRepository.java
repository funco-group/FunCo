package com.found_404.funco.trade.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.found_404.funco.trade.domain.OpenTrade;

public interface QueryDslOpenTradeRepository {

	List<OpenTrade> findMyOpenTrade(Long memberId, String ticker, Pageable pageable);

	// 모든 멤버 별 총 지정가 거래 주문 금액 조회
	Map<Long, Long> findAllMemberIdToOrderCash();
}
