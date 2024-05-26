package com.found_404.funco.trade.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.trade.domain.ActiveFuture;

public interface ActiveFutureRepository extends JpaRepository<ActiveFuture, Long> {
	Optional<ActiveFuture> findByMemberIdAndTicker(Long memberId, String ticker);

	List<ActiveFuture> findAllByIdIn(Collection<Long> id);

	List<ActiveFuture> findByMemberId(Long memberId);
}
