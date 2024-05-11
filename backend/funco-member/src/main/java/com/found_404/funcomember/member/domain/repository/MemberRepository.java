package com.found_404.funcomember.member.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funcomember.auth.dto.OauthId;
import com.found_404.funcomember.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
	Optional<Member> findByOauthId(OauthId oauthId);

	boolean existsByNickname(String nickname);
}
