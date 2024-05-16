package com.found_404.funco.note.dto.response;

import com.found_404.funco.feignClient.dto.SimpleMember;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotesResponse (
    Long noteId,
    SimpleMember member,
    String thumbnailImage,
    String thumbnailContent,
    String title,
    String ticker,
    LocalDateTime writeDate,
    Long likeCount,
    Boolean liked,
    Long commentCount
) {



}
