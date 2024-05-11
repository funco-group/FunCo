package com.found_404.funcomember.global.util;

import org.springframework.core.convert.converter.Converter;

import com.found_404.funcomember.auth.type.OauthServerType;

public class OauthServerTypeConverter implements Converter<String, OauthServerType> {

	@Override
	public OauthServerType convert(String source) {
		return OauthServerType.fromName(source);
	}
}
