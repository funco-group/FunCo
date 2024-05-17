package com.found_404.funcomember.member.dto;

import java.util.List;

import com.found_404.funcomember.feignClient.dto.HoldingCoinResponse;

import lombok.Builder;

@Builder
public record MemberAssetInfo(
	Long cash,
	List<HoldingCoinResponse> coins
) {
}
