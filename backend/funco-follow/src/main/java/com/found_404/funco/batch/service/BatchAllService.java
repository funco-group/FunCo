package com.found_404.funco.batch.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.found_404.funco.follow.domain.repository.FollowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchAllService {
	private final FollowRepository followRepository;

	public Map<Long, Long> readFollowerInvestmentList() {
		Map<Long, Long> followerInvestmentList = followRepository.findFollowerInvestmentList();
		return followerInvestmentList != null ? followerInvestmentList : new HashMap<>();
	}

	public Map<Long, Long> readFollowingInvestmentList() {
		Map<Long, Long> followingInvestmentList = followRepository.findFollowingInvestmentList();
		return followingInvestmentList != null ? followingInvestmentList : new HashMap<>();
	}
}
