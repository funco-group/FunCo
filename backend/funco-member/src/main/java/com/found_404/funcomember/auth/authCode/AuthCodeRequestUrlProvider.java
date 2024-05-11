package com.found_404.funcomember.auth.authCode;

import com.found_404.funcomember.auth.type.OauthServerType;

public interface AuthCodeRequestUrlProvider {
	OauthServerType supportServer();

	String provide();
}
