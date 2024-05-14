package com.found_404.funco.note.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record NotesResponse (
    Long noteId,
    NoteMemberResponse member,
    String thumbnailImage,
    String thumbnailContent,
    String title,
    String ticker,
    LocalDateTime writeDate,
    Long likeCount,
    boolean liked,
    Long commmentCount
) {



}
