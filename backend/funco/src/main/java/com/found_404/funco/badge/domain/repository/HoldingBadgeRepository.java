package com.found_404.funco.badge.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.badge.domain.HoldingBadge;

public interface HoldingBadgeRepository extends JpaRepository<HoldingBadge, Long> {
}
