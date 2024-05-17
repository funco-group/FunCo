package com.found_404.funcomember.member.domain.repository;

import com.found_404.funcomember.member.dto.MemberInfo;

public interface MemberCustomRepository {
	MemberInfo findMyInfoByMemberId(Long memberId);
}
