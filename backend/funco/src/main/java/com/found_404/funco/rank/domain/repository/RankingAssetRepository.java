package com.found_404.funco.rank.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.rank.domain.RankingAsset;

public interface RankingAssetRepository extends JpaRepository<RankingAsset, Long> {
}
