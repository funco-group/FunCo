package com.found_404.funco.note.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record NoteCommentResponse(
    List<CommentsResponse> comments,
    Integer commentCount
) {
}
