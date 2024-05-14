package com.found_404.funco.note.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NoteErrorCode {


    NOT_FOUND_NOTE(HttpStatus.BAD_REQUEST, "해당 게시글을 찾을 수 없습니다."),
    INVALID_FILTER(HttpStatus.BAD_REQUEST, "유효하지 않은 필터입니다.");

    private final HttpStatus httpStatus;
    private final String errorMsg;
}
