package com.found_404.funco.trade.controller;

import com.found_404.funco.global.memberIdHeader.AuthMemberId;
import com.found_404.funco.trade.dto.FutureTrade;
import com.found_404.funco.trade.dto.request.RequestFutures;
import com.found_404.funco.trade.dto.request.RequestBuyFutures;
import com.found_404.funco.trade.dto.ActiveFutureDto;
import com.found_404.funco.trade.service.FutureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trade/futures")
@RequiredArgsConstructor
public class FuturesController {

    private final FutureService futureService;

    @PostMapping("/long")
    public ResponseEntity<?> buyFuturesLong(@AuthMemberId Long memberId, @Valid RequestBuyFutures requestBuyFutures) {
        futureService.buyFuturesLong(memberId, requestBuyFutures);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/short")
    public ResponseEntity<?> buyFuturesShort(@AuthMemberId Long memberId, @Valid RequestBuyFutures requestBuyFutures) {
        futureService.buyFuturesShort(memberId, requestBuyFutures);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{futureId}/settlement")
    public ResponseEntity<?> settleFutures(@AuthMemberId Long memberId, @PathVariable Long futureId) {
        futureService.settlement(memberId, futureId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/active")
    public ResponseEntity<ActiveFutureDto> getActiveFutures(@AuthMemberId Long memberId, @Valid RequestFutures requestFutures) {
        return ResponseEntity.ok(futureService.getActiveFutures(memberId, requestFutures.ticker()));
    }

    @GetMapping()
    public ResponseEntity<List<FutureTrade>> getFutures(@AuthMemberId Long memberId, @Valid RequestFutures requestFutures) {
        return ResponseEntity.ok(futureService.getFutures(memberId, requestFutures.ticker()));
    }

}
