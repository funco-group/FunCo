package com.found_404.funco.note.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NoteCommentErrorCode {
    NOT_FOUND_NOTE_COMMENT(HttpStatus.BAD_REQUEST, "해당 댓글을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMsg;
}
