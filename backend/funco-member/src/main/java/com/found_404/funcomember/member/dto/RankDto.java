package com.found_404.funcomember.member.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

import lombok.Builder;

@Builder
public record RankDto(
	Long rank,
	MemberInfo member,
	Double returnRate,
	Long totalAsset,
	Long followingAsset
) {
	public static List<RankDto> from(Set<ZSetOperations.TypedTuple<Object>> typedTuples, Long offset) {
		if (CollectionUtils.isEmpty(typedTuples)) {
			return Collections.emptyList();
		}
		AtomicInteger index = new AtomicInteger(0); // 인덱스를 저장할 AtomicInteger 생성
		return typedTuples.stream()
			.map(tuple -> {
				RankDto rankDto = (RankDto)tuple.getValue();
				return RankDto.builder()
					.rank(offset + Long.valueOf(index.incrementAndGet())) // 인덱스를 증가시키고 rank에 저장
					.member(rankDto.member())
					.returnRate(rankDto.returnRate())
					.totalAsset(rankDto.totalAsset())
					.followingAsset(rankDto.followingAsset())
					.build();
			})
			.collect(Collectors.toList());
	}
}
