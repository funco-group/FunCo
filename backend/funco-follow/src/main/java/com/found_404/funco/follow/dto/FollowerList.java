package com.found_404.funco.follow.dto;

import com.found_404.funco.follow.domain.Follow;

import java.util.List;

public record FollowerList(
        List<Follow> follows,
        Boolean last
) {
}
