package com.found_404.funco.note.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CommentsResponse(
    Long commentId,
    NoteMemberResponse member,
    List<CommentsResponse> childComments,
    String content,
    LocalDateTime date

) {
}