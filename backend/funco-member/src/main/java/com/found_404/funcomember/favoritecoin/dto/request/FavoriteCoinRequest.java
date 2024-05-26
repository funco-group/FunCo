package com.found_404.funcomember.favoritecoin.dto.request;

import lombok.Builder;

@Builder
public record FavoriteCoinRequest(
	String ticker
) {
}
