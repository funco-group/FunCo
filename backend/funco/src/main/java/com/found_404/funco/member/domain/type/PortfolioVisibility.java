package com.found_404.funco.member.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioVisibility {
	PUBLIC("public"),
	PRIVATE("private"),
	SUBSCRIBE("subscribe");

	private final String value;
}
