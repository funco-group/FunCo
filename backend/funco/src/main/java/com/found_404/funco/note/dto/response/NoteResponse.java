package com.found_404.funco.note.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

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