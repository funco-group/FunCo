package com.found_404.funco.follow.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.follow.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long>, QueryDslFollowRepository {
	Optional<Follow> findFollowByFollowingMemberIdAndFollowerMemberIdAndSettledFalse(Long followingMemberId,
		Long followerMemberId);

	List<Follow> findAllByFollowerMemberId(Long followerMemberId);

	@EntityGraph(attributePaths = {"following","follower"})
	List<Follow> findAllByFollowerMemberIdAndSettled(Long followerMemberId, Boolean settled);

	List<Follow> findAllByFollowerMemberIdAndSettledFalse(Long followerMemberId);

    List<Follow> findAllByFollowerMemberIdAndSettledTrue(Long followerMemberId);

	List<Follow> findAllByFollowingMemberIdAndSettledTrue(Long followingMemberId);
}
