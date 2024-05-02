package com.found_404.funco.note.controller;

import com.found_404.funco.global.util.AuthMemberId;
import com.found_404.funco.member.domain.Member;
import com.found_404.funco.note.dto.request.NoteRequest;
import com.found_404.funco.note.dto.request.NotesFilterRequest;
import com.found_404.funco.note.dto.response.CommentsResponse;
import com.found_404.funco.note.dto.response.NoteResponse;
import com.found_404.funco.note.dto.response.NotesResponse;
import com.found_404.funco.note.service.NoteService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/notes")
@RequiredArgsConstructor
@RestController
public class NoteController {

    private final NoteService noteService;

    // 게시글 목록 조회
    @GetMapping()
    public ResponseEntity<List<NotesResponse>> getNotes(
        @AuthMemberId Long memberId,
        @ModelAttribute  NotesFilterRequest notesFilterRequest) {
//        Long memberId = 1L;

        return ResponseEntity.ok(noteService.getNotes(memberId, notesFilterRequest));
    }

    // 게시글 상세 조회
    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponse> getNote(@PathVariable Long noteId) {
        return ResponseEntity.ok(noteService.getNote(noteId));
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<?> addNote(
        @AuthenticationPrincipal Member member,
        @RequestBody @Valid NoteRequest request) {

        noteService.addNote(member, request);
        return ResponseEntity.ok().build();
    }

    // 이미지 업로드


    // 게시글 수정
//    @PutMapping("/{noteId}")

    // 게시글 삭제
    @DeleteMapping("/{noteId}")
    public ResponseEntity<?> removeNote(
        @AuthMemberId Long memberId,
        @PathVariable Long noteId) {
        noteService.removeNote(memberId, noteId);
        return ResponseEntity.ok().build();
    }

    // 댓글 목록 조회
    @GetMapping("/{noteId}/comments")
    public ResponseEntity<List<CommentsResponse>> getComments(
        @PathVariable Long noteId
    ) {
        return ResponseEntity.ok(noteService.getComments(noteId));
    }

}
