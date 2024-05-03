package com.found_404.funco.note.service;

import static com.found_404.funco.note.exception.NoteCommentErrorCode.NOT_FOUND_NOTE_COMMENT;

import com.found_404.funco.member.domain.Member;
import com.found_404.funco.note.domain.NoteComment;
import com.found_404.funco.note.domain.repository.NoteCommentRepository;
import com.found_404.funco.note.dto.request.EditNoteCommentRequest;
import com.found_404.funco.note.exception.NoteCommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteCommentService {
    
    private final NoteCommentRepository noteCommentRepository;

    @Transactional
    public void editComment(Member member, Long commentId, EditNoteCommentRequest request) {
        NoteComment comment = noteCommentRepository.findById(commentId).orElseThrow(() -> new NoteCommentException(NOT_FOUND_NOTE_COMMENT));
        if (comment.getMember().getId().equals(member.getId())) {
            comment.editNoteComment(request.content());
        }
    }
}
