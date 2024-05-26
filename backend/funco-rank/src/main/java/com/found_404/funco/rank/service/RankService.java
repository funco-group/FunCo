package com.found_404.funco.rank.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.rank.domain.type.RankType;
import com.found_404.funco.rank.dto.response.RankResponse;
import com.found_404.funco.rank.dto.response.RankingResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RankService {

	private final RedisTemplate<String, Object> rankZSetRedisTemplate;

	public Page<RankResponse> readRanking(String type, Pageable pageable) {
		ZSetOperations<String, Object> zSetOperations = rankZSetRedisTemplate.opsForZSet();
		String zSetName = type.toUpperCase().equals(RankType.ASSET.toString()) ? RankType.ASSET.getDescription() :
			RankType.FOLLOWER_CASH.getDescription();

		// 페이징 처리된 결과 가져오기
		Set<ZSetOperations.TypedTuple<Object>> typedTuples = zSetOperations.reverseRangeWithScores(zSetName,
			pageable.getOffset(),
			pageable.getOffset() + pageable.getPageSize() - 1);

		// 가져온 결과를 RankResponse 객체로 변환
		List<RankResponse> rankResponses = RankResponse.from(typedTuples, pageable.getOffset());
		// 전체 결과 수 가져오기
		Long totalElements = zSetOperations.zCard(zSetName);

		// Page 객체 생성
		return new PageImpl<>(rankResponses, pageable, totalElements);
	}

	public RankingResponse getRankingInfo(String type, Long memberId) {
		Set<ZSetOperations.TypedTuple<Object>> typedTuples = rankZSetRedisTemplate.opsForZSet().reverseRangeWithScores(type,
			0, -1);

		AtomicInteger index = new AtomicInteger(0); // 인덱스를 저장할 AtomicInteger 생성
		Optional<Long> ranking = typedTuples.stream()
			.map(tuple -> (RankResponse)tuple.getValue())
			.filter(Objects::nonNull)
			.filter(rankResponse -> {
				index.incrementAndGet(); // 인덱스 증가
				return Objects.equals(rankResponse.member().id(), memberId); // 조건 확인
			})
			.findFirst()
			.map(__ -> index.longValue());

		return RankingResponse.builder()
			.rank(ranking.orElse(null))
			.build();
	}
}
