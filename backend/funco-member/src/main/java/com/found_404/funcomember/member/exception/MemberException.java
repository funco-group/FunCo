package com.found_404.funcomember.member.exception;

import com.found_404.funcomember.global.exception.BaseException;

import lombok.Getter;

@Getter
public class MemberException extends BaseException {
	public MemberException(MemberErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getErrorMsg());
	}
}
