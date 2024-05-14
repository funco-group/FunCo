package com.found_404.funco.note.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record CommentsResponse(
    Long commentId,
    NoteMemberResponse member,
    List<CommentsResponse> childComments,
    String content,
    LocalDateTime date

) {
}