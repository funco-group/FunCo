package com.found_404.funco.note.exception;

import com.found_404.funco.global.exception.BaseException;

public class NoteException extends BaseException {

    public NoteException(NoteErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getErrorMsg());
    }
}
