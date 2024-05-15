package com.found_404.funcomember.member.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funcomember.member.domain.Member;
import com.found_404.funcomember.member.domain.OauthId;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
	Optional<Member> findByOauthId(OauthId oauthId);

	List<Member> findAllByIdIn(Collection<Long> id);

	boolean existsByNickname(String nickname);
}
