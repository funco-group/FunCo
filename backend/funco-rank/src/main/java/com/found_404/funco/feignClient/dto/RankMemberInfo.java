package com.found_404.funco.feignClient.dto;

public record RankMemberInfo(
	Long id,
	String nickname,
	String profileUrl,
	String introduction,
	Long cash,
	String portfolioStatus,
	Long portfolioPrice
) {
}
