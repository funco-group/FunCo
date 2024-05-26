package com.found_404.funcomember.hello.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funcomember.hello.domain.Hello;

public interface HelloRepository extends JpaRepository<Hello, Long> {

}
