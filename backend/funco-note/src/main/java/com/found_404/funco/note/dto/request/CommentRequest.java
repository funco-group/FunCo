package com.found_404.funco.note.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(

    Long parentCommentId,

    @NotBlank
    String content

) {

}
