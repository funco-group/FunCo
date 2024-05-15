package com.found_404.funco.note.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

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
