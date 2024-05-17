package com.found_404.funco.trade.service;

import com.found_404.funco.trade.domain.repository.ActiveFutureRepository;
import com.found_404.funco.trade.domain.repository.FutureTradeRepository;
import com.found_404.funco.trade.dto.FuturesDto;
import com.found_404.funco.trade.dto.request.RequestBuyFutures;
import com.found_404.funco.trade.dto.ActiveFutureDto;
import com.found_404.funco.trade.exception.TradeErrorCode;
import com.found_404.funco.trade.exception.TradeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.found_404.funco.trade.exception.TradeErrorCode.NOT_FOUND_TRADE;

@Service
@Slf4j
@RequiredArgsConstructor
public class FutureService {
    private final ActiveFutureRepository activeFutureRepository;
    private final FutureTradeRepository futureTradeRepository;


    public void buyFuturesLong(Long memberId, RequestBuyFutures requestBuyFutures) {

    }

    public void buyFuturesShort(Long memberId, RequestBuyFutures requestBuyFutures) {

    }

    public void settlement(Long memberId, String futureId) {

    }

    public ActiveFutureDto getActiveFutures(Long memberId, String ticker) {
        return ActiveFutureDto.fromEntity(activeFutureRepository.findByMemberIdAndTicker(memberId, ticker)
                .orElseThrow(()->new TradeException(NOT_FOUND_TRADE)));
    }

    public List<FuturesDto> getFutures(Long memberId, String ticker) {
        return futureTradeRepository.findAllByMemberIdAndTicker(memberId, ticker)
                .stream()
                .map(FuturesDto::fromEntity)
                .toList();
    }
}
