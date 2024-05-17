package com.found_404.funco.trade.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.trade.domain.ActiveFuture;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ActiveFutureRepository extends JpaRepository<ActiveFuture, Long> {
    Optional<ActiveFuture> findByMemberIdAndTicker(Long memberId, String ticker);

    List<ActiveFuture> findAllByIdIn(Collection<Long> id);
}
