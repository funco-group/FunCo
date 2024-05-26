package com.found_404.funco.batch.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.found_404.funco.follow.domain.repository.FollowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchAllService {
	private final FollowRepository followRepository;

	public Map<Long, Long> readFollowerInvestmentList() {
		return followRepository.findFollowerInvestmentList();
	}

	public Map<Long, Long> readFollowingInvestmentList() {
		return followRepository.findFollowingInvestmentList();
	}
}
