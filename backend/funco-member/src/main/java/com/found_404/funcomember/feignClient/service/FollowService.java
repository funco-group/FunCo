package com.found_404.funcomember.feignClient.service;

import static com.found_404.funcomember.member.exception.MemberErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.found_404.funcomember.feignClient.client.FollowServiceClient;
import com.found_404.funcomember.feignClient.dto.FollowAssetResponse;
import com.found_404.funcomember.feignClient.dto.FollowerInfoResponse;
import com.found_404.funcomember.member.exception.MemberException;
import com.found_404.funcomember.portfolio.dto.request.FollowerProfitRequest;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class FollowService {
	private final FollowServiceClient followServiceClient;
	private final String SERVER_NAME = "[follow-service]";

	public Long getInvestments(Long memberId) {
		try {
			return followServiceClient.getInvestments(memberId).investments();
		} catch (FeignException e) {
			log.error("{} get investments error : {}", SERVER_NAME, e.getMessage());
			throw new MemberException(FOLLOW_SERVER_ERROR);
		}
	}

	public List<FollowerInfoResponse> getFollowerInfos(Long followingId) {
		try {
			return followServiceClient.getFollowerInfos(followingId);
		} catch (FeignException e) {
			log.error("{} get followerInfos error : {}", SERVER_NAME, e.getMessage());
			throw new MemberException(FOLLOW_SERVER_ERROR);
		}
	}

	public void modifyFollower(Long followingId, FollowerProfitRequest followerProfitRequest) {
		try {
			followServiceClient.modifyFollower(followingId, followerProfitRequest);
		} catch (FeignException e) {
			log.error("{} modify Follower error : {}", SERVER_NAME, e.getMessage());
			throw new MemberException(FOLLOW_SERVER_ERROR);
		}
	}

	public Boolean getFollowStatus(Long loginMemberId, Long targetMemberId) {
		try {
			return followServiceClient.getFollowStatus(loginMemberId, targetMemberId).followed();
		} catch (FeignException e) {
			log.error("{} get followStatus error : {}", SERVER_NAME, e.getMessage());
			throw new MemberException(FOLLOW_SERVER_ERROR);
		}
	}

	public FollowAssetResponse getFollowAsset(Long memberId) {
		try {
			return followServiceClient.getFollowAsset(memberId);
		} catch (FeignException e) {
			log.error("{} get followAsset error : {}", SERVER_NAME, e.getMessage());
			throw new MemberException(FOLLOW_SERVER_ERROR);
		}
	}
}
