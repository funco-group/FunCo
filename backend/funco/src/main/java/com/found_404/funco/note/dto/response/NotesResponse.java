package com.found_404.funco.note.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record NotesResponse (
    Long noteId,

    String nickname,

    String profileImage,

    String thumbnail,

    String title,

    String content,

    String coinName,

    LocalDateTime writeDate,

    Long likeCount,

    boolean liked,

    Long commmentCount
) {



}
