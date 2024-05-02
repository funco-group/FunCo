package com.found_404.funco.badge.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.badge.domain.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

}
