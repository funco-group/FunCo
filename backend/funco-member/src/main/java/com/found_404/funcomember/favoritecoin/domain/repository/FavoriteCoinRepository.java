package com.found_404.funcomember.favoritecoin.domain.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.found_404.funcomember.favoritecoin.domain.FavoriteCoin;
import com.found_404.funcomember.member.domain.Member;

public interface FavoriteCoinRepository extends JpaRepository<FavoriteCoin, Long>, FavoriteCoinCustomRepository {
	@Transactional
	void deleteAllByMemberAndTickerNotIn(Member member, Set<String> tickers);
}
