package com.found_404.funco.rank.service;

import static com.found_404.funco.global.util.DecimalCalculator.*;
import static com.found_404.funco.global.util.ScaleType.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.found_404.funco.feignClient.dto.RankMemberInfo;
import com.found_404.funco.feignClient.service.FollowService;
import com.found_404.funco.feignClient.service.MemberService;
import com.found_404.funco.feignClient.service.TradeService;
import com.found_404.funco.rank.domain.type.RankType;
import com.found_404.funco.rank.dto.HoldingCoinInfo;
import com.found_404.funco.rank.dto.MemberInfo;
import com.found_404.funco.rank.dto.response.RankResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RankSchedulerService {

	private final RedisTemplate<String, Object> rankZSetRedisTemplate;
	private final TradeService tradeService;
	private final MemberService memberService;
	private final FollowService followService;

	private final static long INIT_CASH = 10_000_000;
	private final static long PERCENT = 100;

	// 배치 전 - 스케줄링 돌릴 메서드
	@Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Seoul") // 1분마다 실행
	@PostConstruct
	public void runSchedulingProcess() {
		log.info("랭킹 스케줄링 작업 시간 : " + LocalDateTime.now());
		// redis 비우는 작업
		clearRankingZSets();
		// 코인 및 가격 정보 조회
		Map<String, Double> tickerPrice = tradeService.getCryptoPrice(tradeService.getHoldingCoinList());
		// 코인 자산 및 팔로워 자산 계산 및 랭킹 업데이트
		calculateAndSetRanking(tickerPrice);

		log.info("랭킹 스케줄링 작업 종료 : " + LocalDateTime.now());
	}

	// redis ZSET 비우는 메서드
	private void clearRankingZSets() {
		rankZSetRedisTemplate.opsForZSet().removeRange(RankType.FOLLOWER_CASH.getDescription(), 0, -1);
		rankZSetRedisTemplate.opsForZSet().removeRange(RankType.ASSET.getDescription(), 0, -1);
	}

	// 코인 자산 및 팔로워 자산 계산 및 랭킹 업데이트
	private void calculateAndSetRanking(Map<String, Double> tickerPrice) {
		// 멤버 별 보유 코인 금액
		Map<Long, Long> holdingCoins = calculateHoldingCoins(tickerPrice);

		// 모든 멤버 리스트
		List<RankMemberInfo> rankMemberInfoList = memberService.getAllMemberList();

		// 모든 멤버별 총 따라오는 투자금
		Map<Long, Long> followerInvestmentMap = followService.getFollowerInvestmentList();

		// 모든 멤버별 총 투자한 투자금
		Map<Long, Long> followingInvestmentMap = followService.getFollowingInvestmentList();

		// 모든 멤버별 총 지정가 거래 주문 금액
		Map<Long, Long> openTradeOrderCashMap = tradeService.getOpenTradeOrderCashList();

		// List<FollowingCoinInfo> followingCoinInfos = rankCustomRepository.findFollowingCoinInfo();

		// 랭킹 업데이트
		rankMemberInfoList.forEach(info -> {
			// 총 자산 = 가용 현금 + 총 보유 코인 가격 + 팔로우 투자금 + 지정가 거래 주문 금액
			long totalAsset = info.cash() + (holdingCoins.getOrDefault(info.id(), 0L)) +
				followingInvestmentMap.getOrDefault(info.id(), 0L) + openTradeOrderCashMap.getOrDefault(info.id(), 0L);

			updateRankingInRedis(RankResponse.builder()
				.member(MemberInfo.rankMemberInfoToMemberInfo(info))
				.returnRate(
					multiple(divide(totalAsset - INIT_CASH, INIT_CASH, NORMAL_SCALE), PERCENT, RETURN_RATE_SCALE))
				.totalAsset(totalAsset)
				.followingAsset(followerInvestmentMap.getOrDefault(info.id(), 0L))
				.build());
		});
	}

	// 코인 자산 계산
	private Map<Long, Long> calculateHoldingCoins(Map<String, Double> tickerPrice) {
		if (CollectionUtils.isEmpty(tickerPrice)) {
			return Collections.emptyMap();
		}

		List<HoldingCoinInfo> holdingCoinInfos = tradeService.getHoldingCoinInfoList();
		return holdingCoinInfos.stream()
			.collect(Collectors.toMap(
				HoldingCoinInfo::memberId,
				info -> (long)multiple(tickerPrice.get(info.ticker()), info.volume(), CASH_SCALE),
				Long::sum
			));
	}

	// redis에 랭킹 업데이트
	private void updateRankingInRedis(RankResponse rankResponse) {
		rankZSetRedisTemplate.opsForZSet()
			.add(RankType.FOLLOWER_CASH.getDescription(), rankResponse, rankResponse.followingAsset());
		rankZSetRedisTemplate.opsForZSet()
			.add(RankType.ASSET.getDescription(), rankResponse, rankResponse.totalAsset());
	}
}
