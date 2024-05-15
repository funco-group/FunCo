package com.found_404.funcomember.feignClient.service;

import com.found_404.funcomember.feignClient.client.FollowServiceClient;
import com.found_404.funcomember.feignClient.dto.FollowerInfoResponse;
import com.found_404.funcomember.member.exception.MemberException;
import com.found_404.funcomember.portfolio.dto.request.FollowerProfitRequest;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import static com.found_404.funcomember.member.exception.MemberErrorCode.FOLLOW_SERVER_ERROR;

import java.util.List;

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

}
