package com.found_404.funcomember.member.domain.type;

import static java.util.Locale.*;

public enum OauthServerType {
	GOOGLE,
	;

	public static OauthServerType fromName(String type) {
		return OauthServerType.valueOf(type.toUpperCase(ENGLISH));
	}
}
