package com.found_404.funcomember.portfolio.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funcomember.member.domain.Member;
import com.found_404.funcomember.member.domain.repository.MemberRepository;
import com.found_404.funcomember.member.domain.type.PortfolioStatusType;
import com.found_404.funcomember.member.exception.MemberErrorCode;
import com.found_404.funcomember.member.exception.MemberException;
import com.found_404.funcomember.portfolio.domain.Subscribe;
import com.found_404.funcomember.portfolio.domain.repository.SubscribeRepository;
import com.found_404.funcomember.portfolio.dto.FollowerInfo;
import com.found_404.funcomember.portfolio.dto.request.PortfolioStatusRequest;
import com.found_404.funcomember.portfolio.dto.request.SubscribeRequest;
import com.found_404.funcomember.portfolio.exception.PortfolioErrorCode;
import com.found_404.funcomember.portfolio.exception.PortfolioException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PortfolioService {
	private final MemberRepository memberRepository;
	private final SubscribeRepository subscribeRepository;

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
		Member seller = findByMemberId(subscribeRequest.memberId());

		// 포트폴리오 가격 확인
		Long portfolioPrice = seller.getPortfolioPrice();

		// subscirber의 가용현금으로 seller의 포트폴리오 살 수 없다면 에러 발생
		if (subscriber.getCash() < portfolioPrice) {
			throw new PortfolioException(PortfolioErrorCode.INSUFFICIENT_CASH);
		}

		// double ratio = divide(portfolioPrice, seller.getCash(), ScaleType.RETURN_RATE_SCALE);
		//
		// subscriber.decreaseCash(portfolioPrice);
		// seller.increaseCashWithoutCommission(portfolioPrice);

		// 구독
		subscribe(subscriber, seller);

		// seller의 팔로워들 동기화(더해줌)
		//synchronizeFollowers(seller.getId(), ratio);

		// subscriber의 팔로워들 동기화(빼줌)
		//synchronizeFollowers(subscriber.getId(), -ratio);
	}

	private Member findByMemberId(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
	}

	public void synchronizeFollowers(Long followingId, double ratio) {
		// List<FollowerInfo> followerInfos = subscribeRepository.findFollowInfoByFollowingId(followingId);
		// followerInfos.forEach(
		// 	info -> {
		// 		long cash = info.cash() + (long)multiple(info.cash(), ratio, ScaleType.CASH_SCALE);
		// 		subscribeRepository.updateFollower(followingId, info.followerId(), cash);
		// 	}
		// );
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
