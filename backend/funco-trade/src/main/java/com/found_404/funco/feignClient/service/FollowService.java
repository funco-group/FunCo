package com.found_404.funco.feignClient.service;

import com.found_404.funco.feignClient.client.FollowServiceClient;
import com.found_404.funco.feignClient.dto.FollowTradeRequest;
import com.found_404.funco.trade.domain.Trade;
import com.found_404.funco.trade.exception.TradeException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.found_404.funco.trade.exception.TradeErrorCode.FOLLOW_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowService {
	private final FollowServiceClient followServiceClient;
	private final String SERVER_NAME = "[follow-service]";

	@Async
	public void createFollowTrade(List<Trade> trades) {
		callFollowTrade(trades);
	}

	@Async
	public void createFollowTrade(Trade trade) {
		callFollowTrade(List.of(trade));
	}

	private void callFollowTrade(List<Trade> trades) {
		try {
			followServiceClient.createFollowTrade(trades.stream()
					.map(FollowTradeRequest::fromEntity)
					.toList());
		} catch (FeignException e) {
			log.error("{} create Follow Trade error : {}", SERVER_NAME, e.getMessage());
			throw new TradeException(FOLLOW_SERVER_ERROR);
		}
	}
}
