package com.found_404.funco.note.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NoteErrorCode {

    NOT_FOUND_NOTE(HttpStatus.BAD_REQUEST, "해당 게시글을 찾을 수 없습니다."),
    INVALID_FILTER(HttpStatus.BAD_REQUEST, "유효하지 않은 필터입니다."),
    API_SERVER_ERROR(HttpStatus.BAD_REQUEST, "외부 요청 서버 에러"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한 없음."),

    NOT_FOUND_IMAGE(HttpStatus.BAD_REQUEST, "이미지를 찾을 수 없습니다."),
    IMAGE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 실패"),
    MISS_MATCH_IMAGE_TYPE(HttpStatus.BAD_REQUEST, "이미지 타입 에러"),
    ;

    private final HttpStatus httpStatus;
    private final String errorMsg;
}
