package com.found_404.funco.batch.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.found_404.funco.trade.domain.repository.HoldingCoinRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchAllService {

	private final HoldingCoinRepository holdingCoinRepository;

	public List<String> readHoldingCoinList() {
		return holdingCoinRepository.findHoldingCoin();
	}
}
