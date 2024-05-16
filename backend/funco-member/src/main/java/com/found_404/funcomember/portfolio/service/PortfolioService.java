package com.found_404.funcomember.portfolio.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funcomember.feignClient.dto.FollowerInfoResponse;
import com.found_404.funcomember.feignClient.service.FollowService;
import com.found_404.funcomember.feignClient.service.TradeService;
import com.found_404.funcomember.member.domain.Member;
import com.found_404.funcomember.member.domain.repository.MemberRepository;
import com.found_404.funcomember.member.domain.type.PortfolioStatusType;
import com.found_404.funcomember.member.exception.MemberErrorCode;
import com.found_404.funcomember.member.exception.MemberException;
import com.found_404.funcomember.portfolio.domain.Subscribe;
import com.found_404.funcomember.portfolio.domain.repository.SubscribeRepository;
import com.found_404.funcomember.portfolio.dto.request.FollowerProfitRequest;
import com.found_404.funcomember.portfolio.dto.request.PortfolioStatusRequest;
import com.found_404.funcomember.portfolio.dto.request.ProfitRequest;
import com.found_404.funcomember.portfolio.dto.request.ReturnRateRequest;
import com.found_404.funcomember.portfolio.dto.request.SubscribeRequest;
import com.found_404.funcomember.portfolio.exception.PortfolioErrorCode;
import com.found_404.funcomember.portfolio.exception.PortfolioException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PortfolioService {
	private final MemberRepository memberRepository;
	private final SubscribeRepository subscribeRepository;
	private final FollowService followService;
	private final TradeService tradeService;

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

		// 포트폴리오 가격 확인
		Long portfolioPrice = seller.getPortfolioPrice();

		// subscirber의 가용현금으로 seller의 포트폴리오 살 수 없다면 에러 발생
		if (subscriber.getCash() < portfolioPrice) {
			throw new PortfolioException(PortfolioErrorCode.INSUFFICIENT_CASH);
		}

		Double ratio = tradeService.getReturnRate(ReturnRateRequest.builder()
			.portfolioPrice(portfolioPrice)
			.cash(seller.getCash())
			.build());

		subscriber.decreaseCash(portfolioPrice);
		seller.increaseCashWithoutCommission(portfolioPrice);

		// 구독
		subscribe(subscriber, seller);

		// seller의 팔로워들 동기화(더해줌)
		synchronizeFollowers(seller.getId(), ratio);

		// subscriber의 팔로워들 동기화(빼줌)
		synchronizeFollowers(subscriber.getId(), -ratio);
	}

	private Member findByMemberId(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
	}

	public void synchronizeFollowers(Long followingId, double ratio) {
		List<FollowerInfoResponse> followerInfos = followService.getFollowerInfos(followingId);
		followerInfos.forEach(
			info -> {
				long cash = info.cash() + tradeService.getProfit(ProfitRequest.builder()
					.cash(info.cash())
					.ratio(ratio)
					.build());
				followService.modifyFollower(followingId, FollowerProfitRequest
					.builder()
					.followerId(info.followerId())
					.cash(cash)
					.build());
			}
		);
	}

	private void subscribe(Member subscriber, Member seller) {
		subscribeRepository.save(Subscribe.builder()
			.fromMember(subscriber)
			.toMember(seller)
			.orderCash(seller.getPortfolioPrice())
			.expiredAt(LocalDateTime.now().plusWeeks(2))
			.build());
	}
}
