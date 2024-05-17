package com.found_404.funco.note.service;

import com.found_404.funco.note.domain.NoteComment;
import com.found_404.funco.note.domain.repository.NoteCommentRepository;
import com.found_404.funco.note.dto.request.EditNoteCommentRequest;
import com.found_404.funco.note.exception.NoteCommentException;
import com.found_404.funco.note.exception.NoteErrorCode;
import com.found_404.funco.note.exception.NoteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.found_404.funco.note.exception.NoteCommentErrorCode.NOT_FOUND_NOTE_COMMENT;

@Service
@RequiredArgsConstructor
public class NoteCommentService {
    
    private final NoteCommentRepository noteCommentRepository;

    @Transactional
    public void editComment(Long memberId, Long commentId, EditNoteCommentRequest request) {
        NoteComment comment = noteCommentRepository.findById(commentId).orElseThrow(() -> new NoteCommentException(NOT_FOUND_NOTE_COMMENT));
        checkAuthorization(memberId, comment);
        comment.editNoteComment(request.content());
    }

    private static void checkAuthorization(Long memberId, NoteComment comment) {
        if (!Objects.equals(comment.getMemberId(), memberId)) {
            throw new NoteException(NoteErrorCode.UNAUTHORIZED);
        }
    }

    public void removeComment(Long memberId, Long commentId) {
        NoteComment comment = noteCommentRepository.findById(commentId).orElseThrow(() -> new NoteCommentException(NOT_FOUND_NOTE_COMMENT));
        checkAuthorization(memberId, comment);

        // 자식 댓글이 있을 경우 soft delete
        if (noteCommentRepository.existsByParentId(comment.getId())) {
            comment.softDelete();
            return;
        }

        // 자식 댓글이 없을 경우 hard delete
        noteCommentRepository.delete(comment);
    }
}
