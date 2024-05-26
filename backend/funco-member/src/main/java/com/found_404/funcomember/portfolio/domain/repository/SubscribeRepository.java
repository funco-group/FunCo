package com.found_404.funcomember.portfolio.domain.repository;

import com.found_404.funcomember.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funcomember.portfolio.domain.Subscribe;

import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Optional<Subscribe> findByFromMemberAndToMember(Member fromMember, Member toMember);
}
