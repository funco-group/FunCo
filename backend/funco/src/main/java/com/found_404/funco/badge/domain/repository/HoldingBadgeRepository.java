package com.found_404.funco.badge.domain.repository;

import com.found_404.funco.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.badge.domain.HoldingBadge;

public interface HoldingBadgeRepository extends JpaRepository<HoldingBadge, Long> {

    Optional<HoldingBadge> findByMember(Member member);
}
