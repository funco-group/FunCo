package com.found_404.funco.badge.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.badge.domain.WearingBadge;

public interface WearingBadgeRepository extends JpaRepository<WearingBadge, Long> {
}
