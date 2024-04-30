package com.found_404.funco.note.dto.response;

import com.found_404.funco.note.domain.Image;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

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