package com.found_404.funcomember.batch.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.found_404.funcomember.batch.dto.RankMemberInfo;
import com.found_404.funcomember.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchAllService {

	private final MemberRepository memberRepository;

	public List<RankMemberInfo> readAllMemberList() {
		return memberRepository.findAll().stream()
			.map(RankMemberInfo::from)
			.toList();
	}
}
