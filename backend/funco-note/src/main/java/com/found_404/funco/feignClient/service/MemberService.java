package com.found_404.funco.feignClient.service;

import com.found_404.funco.feignClient.client.MemberServiceClient;
import com.found_404.funco.feignClient.dto.SimpleMember;
import com.found_404.funco.note.exception.NoteException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.found_404.funco.note.exception.NoteErrorCode.API_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberServiceClient memberServiceClient;
	private final String SERVER_NAME = "[follow-service]";

	public Long getMemberCash(Long memberId) {
		try {
			return memberServiceClient.getMemberCash(memberId).cash();
		} catch (FeignException e) {
			log.error("member client error : {}", e.getMessage());
			throw new NoteException(API_SERVER_ERROR);
		}
	}

	public Map<Long, SimpleMember> getSimpleMember(Long id) {
		return getSimpleMember(List.of(id));
	}

	public Map<Long, SimpleMember> getSimpleMember(List<Long> ids) {
		try {
			Map<Long, SimpleMember> map = new HashMap<>();
			memberServiceClient.getMembers(ids)
					.forEach(simpleMember -> map.put(simpleMember.id(), simpleMember));

			return map;
		} catch (FeignException e) {
			log.error("member client error : {}", e.getMessage());
			throw new NoteException(API_SERVER_ERROR);
		}
	}


}
