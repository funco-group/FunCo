package com.found_404.funcomember.member.service;

import com.found_404.funcomember.member.domain.Member;
import com.found_404.funcomember.member.domain.repository.MemberRepository;
import com.found_404.funcomember.member.dto.response.UserInfoResponse;
import com.found_404.funcomember.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.found_404.funcomember.member.exception.MemberErrorCode.NOT_FOUND_MEMBER;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {
    private final MemberRepository memberRepository;

    public UserInfoResponse getMyPage(Long loginMemberId, Long memberId) {
        Member member = getMember(memberId);

        return UserInfoResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .introduction(member.getIntroduction())
                // 나머지 채우자
                .portfolioPrice(member.getPortfolioPrice())
                .portfolioStatus(member.getPortfolioStatus())
                .build();
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
    }
}
