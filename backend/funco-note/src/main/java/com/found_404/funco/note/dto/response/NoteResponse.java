package com.found_404.funco.note.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoteResponse(
    Long noteId,
    NoteMemberResponse member,
    String title,
    String content,
    String ticker,
    LocalDateTime writeDate,
    Long likeCount,
    boolean liked,
    Long commentCount

) {
}