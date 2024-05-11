package com.found_404.funcomember.auth.client;

import com.found_404.funcomember.auth.dto.OauthDto;
import com.found_404.funcomember.auth.type.OauthServerType;

public interface OauthMemberClient {
	OauthServerType supportServer();

	OauthDto fetch(String code);
}