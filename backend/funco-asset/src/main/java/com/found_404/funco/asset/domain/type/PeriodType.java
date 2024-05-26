package com.found_404.funco.asset.domain.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PeriodType {
	DAY(0),
	WEEK(1),
	ONEMONTH(1),
	THREEMONTH(3),
	SIXMONTH(6),
	;

	public final int number;
}
