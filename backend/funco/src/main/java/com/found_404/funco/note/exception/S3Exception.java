package com.found_404.funco.note.exception;

import com.found_404.funco.global.exception.BaseException;

public class S3Exception extends BaseException {

    public S3Exception(S3ErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getErrorMsg());
    }

}
