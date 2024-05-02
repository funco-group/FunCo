package com.found_404.funco.portfolio.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.portfolio.domain.Subscribe;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long>, SubscribeCustomRepository {
}
