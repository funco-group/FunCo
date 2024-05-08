package com.found_404.funco.note.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record NoteResponse(
    Long noteId,
    String nickname,
    String profileImage,
    String title,
    String content,
    String coinName,
    LocalDateTime writeDate,
    Long likeCount,
    boolean liked,
    Long commentCount

) {
}