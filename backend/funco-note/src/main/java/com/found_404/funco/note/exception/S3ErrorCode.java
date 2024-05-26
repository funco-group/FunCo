package com.found_404.funco.note.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum S3ErrorCode {

    PUT_OBJECT_EXCEPTION(HttpStatus.BAD_REQUEST, "이미지 업로드에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String errorMsg;

}
