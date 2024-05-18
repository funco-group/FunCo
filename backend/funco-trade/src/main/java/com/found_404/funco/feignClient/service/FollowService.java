package com.found_404.funco.feignClient.service;

import com.found_404.funco.feignClient.client.FollowServiceClient;
import com.found_404.funco.feignClient.dto.FollowTradeRequest;
import com.found_404.funco.trade.domain.FutureTrade;
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

	public void createFollowTrade(List<Trade> trades) {
		callFollowTrade(trades.stream().map(FollowTradeRequest::fromTrade).toList());
	}

	public void createFollowTrade(Trade trade) {
		callFollowTrade(List.of(FollowTradeRequest.fromTrade(trade)));
	}

	public void createFollowTrade(FutureTrade futureTrade) {
		callFollowTradeByFutures(futureTrade);
	}

	@Async
	public void callFollowTradeByFutures(FutureTrade futureTrade) {
		try {
			followServiceClient.callFollowTradeByFutures(futureTrade);
		} catch (FeignException e) {
			log.error("{} create Follow Trade By futures error : {}", SERVER_NAME, e.getMessage());
			throw new TradeException(FOLLOW_SERVER_ERROR);
		}
	}

	@Async
	public void callFollowTrade(List<FollowTradeRequest> followTradeRequests) {
		try {
			followServiceClient.createFollowTrade(followTradeRequests);
		} catch (FeignException e) {
			log.error("{} create Follow Trade error : {}", SERVER_NAME, e.getMessage());
			throw new TradeException(FOLLOW_SERVER_ERROR);
		}
	}
}
