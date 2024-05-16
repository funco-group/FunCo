package com.found_404.funco.note.dto.response;

import com.found_404.funco.feignClient.dto.SimpleMember;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoteResponse(
    Long noteId,
    SimpleMember member,
    String title,
    String content,
    String ticker,
    LocalDateTime writeDate,
    Long likeCount,
    Boolean liked

) {
}