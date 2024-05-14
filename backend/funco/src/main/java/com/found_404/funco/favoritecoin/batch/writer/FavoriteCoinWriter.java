package com.found_404.funco.favoritecoin.batch.writer;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.core.RedisTemplate;

import com.found_404.funco.favoritecoin.batch.dto.FavoriteCoinBatchInfo;
import com.found_404.funco.favoritecoin.domain.FavoriteCoin;
import com.found_404.funco.favoritecoin.domain.repository.FavoriteCoinRepository;
import com.found_404.funco.favoritecoin.dto.FavoriteCoinInfo;
import com.found_404.funco.member.domain.Member;
import com.found_404.funco.member.domain.repository.MemberRepository;
import com.found_404.funco.member.exception.MemberErrorCode;
import com.found_404.funco.member.exception.MemberException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FavoriteCoinWriter implements ItemWriter<FavoriteCoinBatchInfo> {
	private final RedisTemplate<String, Object> favoriteCoinRedisTemplate;
	private final FavoriteCoinRepository favoriteCoinRepository;
	private final MemberRepository memberRepository;

	public FavoriteCoinWriter(RedisTemplate<String, Object> favoriteCoinRedisTemplate,
		FavoriteCoinRepository favoriteCoinRepository, MemberRepository memberRepository) {
		this.favoriteCoinRedisTemplate = favoriteCoinRedisTemplate;
		this.favoriteCoinRepository = favoriteCoinRepository;
		this.memberRepository = memberRepository;
	}

	@Override
	public void write(Chunk<? extends FavoriteCoinBatchInfo> chunk) {
		log.info("=================== write() operates ===================");
		
		chunk.getItems().stream()
			.filter(Objects::nonNull)
			.forEach(info -> {
				// 사용자 정보 가져오기
				Member member = getMemberFromDatabase(info.memberId());

				// Redis에 있는 ticker 정보 가져오기
				Set<String> tickers = info.tickers();

				// Redis에 없는데 MariaDB에 있는 ticker 삭제
				deleteMissingTickersFromDatabase(member, tickers);

				// MariaDB에 없는 ticker 삽입
				insertMissingTickersToDatabase(member, tickers);

				// Redis에서 memberId에 해당하는 빈 Set 행 삭제
				deleteEmptySet(info.memberId().toString());
			});

		log.info("=================== write() end ===================");
	}

	// 사용자 정보 가져오기
	private Member getMemberFromDatabase(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(() -> new MemberException(
			MemberErrorCode.NOT_FOUND_MEMBER));
	}

	// Redis에 없는데 MariaDB에 있는 ticker 삭제
	private void deleteMissingTickersFromDatabase(Member member, Set<String> tickers) {
		favoriteCoinRepository.deleteAllByMemberAndTickerNotIn(member, tickers);
	}

	// MariaDB에 없는 ticker 삽입
	private void insertMissingTickersToDatabase(Member member, Set<String> tickers) {
		List<String> favoriteCoinTickers = favoriteCoinRepository.findFavoriteCoinTickersByMemberId(member.getId());
		favoriteCoinRepository.saveAll(tickers.stream()
			.filter(ticker -> !favoriteCoinTickers.contains(ticker))
			.map(ticker -> FavoriteCoin.builder()
				.member(member)
				.ticker(ticker)
				.build())
			.collect(Collectors.toList()));
	}

	// redis 에서 empty set row 삭제
	private void deleteEmptySet(String updatedKey) {
		FavoriteCoinInfo favoriteCoinInfo = (FavoriteCoinInfo)favoriteCoinRedisTemplate.opsForValue().get(updatedKey);
		if (favoriteCoinInfo == null || favoriteCoinInfo.getTickerSet().isEmpty()) {
			favoriteCoinRedisTemplate.delete(updatedKey);
		}
	}
}