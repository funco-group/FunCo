package com.found_404.funco.note.controller;

import com.found_404.funco.member.domain.Member;
import com.found_404.funco.note.dto.request.EditNoteCommentRequest;
import com.found_404.funco.note.service.NoteCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/comments")
@RequiredArgsConstructor
@RestController
public class NoteCommentController {

    private final NoteCommentService noteCommentService;

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> editComment(
        @AuthenticationPrincipal Member member,
        @PathVariable Long commentId,
        EditNoteCommentRequest request
    ) {
        noteCommentService.editComment(member, commentId, request);
        return ResponseEntity.ok().build();
    }

}
