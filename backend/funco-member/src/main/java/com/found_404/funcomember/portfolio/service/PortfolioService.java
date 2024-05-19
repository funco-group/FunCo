package com.found_404.funcomember.portfolio.service;

import static com.found_404.funcomember.global.util.DecimalCalculator.*;
import static com.found_404.funcomember.global.util.DecimalCalculator.ScaleType.*;
import static com.found_404.funcomember.portfolio.exception.PortfolioErrorCode.INSUFFICIENT_CASH;
import static com.found_404.funcomember.portfolio.exception.PortfolioErrorCode.NOT_PRIVATE_STATUS;

import java.time.LocalDateTime;
import java.util.List;

import com.found_404.funcomember.feignClient.dto.AssetTradeType;
import com.found_404.funcomember.feignClient.service.AssetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funcomember.feignClient.dto.FollowerInfoResponse;
import com.found_404.funcomember.feignClient.service.FollowService;
import com.found_404.funcomember.member.domain.Member;
import com.found_404.funcomember.member.domain.repository.MemberRepository;
import com.found_404.funcomember.member.domain.type.PortfolioStatusType;
import com.found_404.funcomember.member.exception.MemberErrorCode;
import com.found_404.funcomember.member.exception.MemberException;
import com.found_404.funcomember.portfolio.domain.Subscribe;
import com.found_404.funcomember.portfolio.domain.repository.SubscribeRepository;
import com.found_404.funcomember.portfolio.dto.request.FollowerProfitRequest;
import com.found_404.funcomember.portfolio.dto.request.PortfolioStatusRequest;
import com.found_404.funcomember.portfolio.dto.request.SubscribeRequest;
import com.found_404.funcomember.portfolio.exception.PortfolioException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PortfolioService {
	private final MemberRepository memberRepository;
	private final SubscribeRepository subscribeRepository;

	private final FollowService followService;
	private final AssetService assetService;

	@Transactional
	public void updatePortfolioStatus(Long memberId, PortfolioStatusRequest portfolioStatusRequest) {
		Member member = findByMemberId(memberId);

		member.updatePortfolioStatus(
			PortfolioStatusType.valueOf(portfolioStatusRequest.portfolioStatus().toUpperCase()),
			portfolioStatusRequest.portfolioPrice());
	}

	@Transactional
	public void createPortfolio(Long memberId, SubscribeRequest subscribeRequest) {
		Member subscriber = findByMemberId(memberId);
		Member seller = findByMemberId(subscribeRequest.sellerId());

		checkPortfolioStatus(seller);

		// 포트폴리오 가격 확인
		Long portfolioPrice = seller.getPortfolioPrice();

		// subscirber의 가용현금으로 seller의 포트폴리오 살 수 없다면 에러 발생
		checkAvailable(subscriber, portfolioPrice);

		double ratio = divide(portfolioPrice, seller.getCash(), RETURN_RATE_SCALE);

		subscriber.decreaseCash(portfolioPrice);
		seller.increaseCashWithoutCommission(portfolioPrice);

		// 구독
		Subscribe subscribe = subscribe(subscriber, seller);

		// seller의 팔로워들 동기화(더해줌)
		synchronizeFollowers(seller.getId(), ratio);

		// subscriber의 팔로워들 동기화(빼줌)
		synchronizeFollowers(subscriber.getId(), -ratio);

		// [API] 통합 자산 변동내역
		assetService.createAssetHistory(subscribe, AssetTradeType.PURCHASE_PORTFOLIO, subscribe.getFromMember().getCash());
		assetService.createAssetHistory(subscribe, AssetTradeType.SELL_PORTFOLIO, subscribe.getToMember().getCash());
	}

	private static void checkAvailable(Member subscriber, Long portfolioPrice) {
		if (subscriber.getCash() < portfolioPrice) {
			throw new PortfolioException(INSUFFICIENT_CASH);
		}
	}

	private static void checkPortfolioStatus(Member seller) {
		if (seller.getPortfolioStatus().equals(PortfolioStatusType.PUBLIC)) {
			throw new PortfolioException(NOT_PRIVATE_STATUS);
		}
	}

	private Member findByMemberId(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
	}

	public void synchronizeFollowers(Long followingId, double ratio) {
		List<FollowerInfoResponse> followerInfos = followService.getFollowerInfos(followingId);
		followerInfos.forEach(
			info -> {
				long cash = info.cash() + (long)multiple(info.cash(), ratio, CASH_SCALE);
				followService.modifyFollower(followingId, FollowerProfitRequest
					.builder()
					.followerId(info.followerId())
					.cash(cash)
					.build());
			}
		);
	}

	private Subscribe subscribe(Member subscriber, Member seller) {
		return subscribeRepository.save(Subscribe.builder()
				.fromMember(subscriber)
				.toMember(seller)
				.orderCash(seller.getPortfolioPrice())
				.expiredAt(LocalDateTime.now().plusWeeks(2))
				.build());
	}
}
