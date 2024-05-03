package com.found_404.funco.note.exception;

import com.found_404.funco.global.exception.BaseException;

public class NoteCommentException extends BaseException {
    public NoteCommentException(NoteCommentErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getErrorMsg());
    }

}
