package com.found_404.funcomember.member.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioVisibility {
	PUBLIC("PUBLIC"),
	PRIVATE("PRIVATE"),
	SUBSCRIBE("SUBSCRIBE");

	private final String value;
}
