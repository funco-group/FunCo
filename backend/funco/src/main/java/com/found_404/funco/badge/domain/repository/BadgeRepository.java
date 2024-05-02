package com.found_404.funco.badge.domain.repository;

import com.found_404.funco.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.badge.domain.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

}
