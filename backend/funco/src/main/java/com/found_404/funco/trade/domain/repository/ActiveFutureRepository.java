package com.found_404.funco.trade.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.member.domain.Member;
import com.found_404.funco.trade.domain.ActiveFuture;

public interface ActiveFutureRepository extends JpaRepository<ActiveFuture, Long> {

	List<ActiveFuture> findActiveFutureByMember(Member member);

}
