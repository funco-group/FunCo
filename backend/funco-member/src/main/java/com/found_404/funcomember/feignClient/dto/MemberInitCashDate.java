package com.found_404.funcomember.feignClient.dto;

import java.time.LocalDateTime;

public record MemberInitCashDate(
	LocalDateTime initCashDate
) {
	public MemberInitCashDate(LocalDateTime initCashDate) {
		this.initCashDate = initCashDate;
	}
}
