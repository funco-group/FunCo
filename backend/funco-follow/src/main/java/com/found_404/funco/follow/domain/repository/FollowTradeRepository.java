package com.found_404.funco.follow.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.follow.domain.Follow;
import com.found_404.funco.follow.domain.FollowTrade;

public interface FollowTradeRepository extends JpaRepository<FollowTrade, Long> {
    List<FollowTrade> findByFollow(Pageable pageable, Follow follow);
}
