package com.found_404.funco.member.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioStatusType {
	PUBLIC("공개"),
	PRIVATE("비공개");

	private final String korean;
}
