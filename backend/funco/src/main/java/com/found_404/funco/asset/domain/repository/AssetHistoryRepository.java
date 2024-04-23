package com.found_404.funco.asset.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.asset.domain.AssetHistory;

public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Long> {
}
