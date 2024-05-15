package com.found_404.funco.note.controller;

import com.found_404.funco.global.memberIdHeader.AuthMemberId;
import com.found_404.funco.note.dto.request.EditNoteCommentRequest;
import com.found_404.funco.note.service.NoteCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/comments")
@RequiredArgsConstructor
@RestController
public class NoteCommentController {

    private final NoteCommentService noteCommentService;

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> editComment(
        @AuthMemberId Long memberId,
        @PathVariable Long commentId,
        @Valid EditNoteCommentRequest request
    ) {
        noteCommentService.editComment(memberId, commentId, request);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> removeComment(
        @AuthMemberId Long memberId,
        @PathVariable Long commentId) {
        noteCommentService.removeComment(memberId, commentId);
        return ResponseEntity.ok().build();
    }


}
