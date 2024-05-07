package com.found_404.funco.rank;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funco.rank.domain.type.RankType;
import com.found_404.funco.rank.dto.response.RankResponse;
import com.found_404.funco.rank.service.RankService;

@ExtendWith(MockitoExtension.class)
public class RankServiceTest {
	@Mock
	private RedisTemplate<String, Object> rankZSetRedisTemplate;
	@Mock
	private ZSetOperations<String, Object> zSetOperations;
	@InjectMocks
	private RankService rankService;

	@BeforeEach
	void setUp() {
		given(rankZSetRedisTemplate.opsForZSet()).willReturn(zSetOperations);
	}

	@Test
	@Transactional(readOnly = true)
	@DisplayName("총 자산 순으로 랭킹 정보 조회 성공")
	void readRankingSuccessByTotalAsset() {
		// given
		Long offset = 0L;
		Long dataSize = 30L;
		int pageNumber = 0;
		int pageSize = 10;

		String rankType = RankType.ASSET.toString();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Set<ZSetOperations.TypedTuple<Object>> set = getTypedTuples(offset, pageSize, rankType);

		given(zSetOperations.reverseRangeWithScores(rankType, pageable.getOffset(),
			pageable.getOffset() + pageable.getPageSize() - 1))
			.willReturn(set);
		given(zSetOperations.zCard(rankType)).willReturn(dataSize);

		// when
		Page<RankResponse> result = rankService.readRanking(rankType, pageable);

		// then
		assertNotNull(result);
		assertEquals(10, result.getContent().size());
		assertEquals(30, result.getTotalElements());

		verify(zSetOperations).reverseRangeWithScores(rankType, pageNumber * pageSize, pageSize - 1);
		verify(zSetOperations).zCard(rankType);
	}

	@Test
	@Transactional(readOnly = true)
	@DisplayName("총 팔로워 금액 순으로 랭킹 정보 조회 성공")
	void readRankingSuccessByFollowingCash() {
		// given
		Long offset = 0L;
		Long dataSize = 30L;
		int pageNumber = 0;
		int pageSize = 10;

		String rankType = RankType.FOLLOWER_CASH.toString();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Set<ZSetOperations.TypedTuple<Object>> set = getTypedTuples(offset, pageSize, rankType);

		given(zSetOperations.reverseRangeWithScores(rankType, pageable.getOffset(),
			pageable.getOffset() + pageable.getPageSize() - 1))
			.willReturn(set);
		given(zSetOperations.zCard(rankType)).willReturn(dataSize);

		// when
		Page<RankResponse> result = rankService.readRanking(rankType, pageable);

		// then
		assertNotNull(result);
		assertEquals(10, result.getContent().size());
		assertEquals(30, result.getTotalElements());

		verify(zSetOperations).reverseRangeWithScores(rankType, pageNumber * pageSize, pageSize - 1);
		verify(zSetOperations).zCard(rankType);
	}

	@NotNull
	private static Set<ZSetOperations.TypedTuple<Object>> getTypedTuples(Long offset, int pageSize, String rankType) {
		Set<ZSetOperations.TypedTuple<Object>> set = new HashSet<>();
		for (int i = 0; i < pageSize; i++) {
			int index = i + 1;
			set.add(new ZSetOperations.TypedTuple<Object>() {
				@Override
				public Object getValue() {
					return RankResponse.builder()
						.rank(offset + Long.valueOf(index)) // 인덱스를 증가시키고 rank에 저장
						.build();
				}

				@Override
				public Double getScore() {
					return (double)index;
				}

				@Override
				public int compareTo(ZSetOperations.TypedTuple<Object> o) {
					return Double.compare(getScore(), o.getScore());
				}
			});
		}
		return set;
	}
}
