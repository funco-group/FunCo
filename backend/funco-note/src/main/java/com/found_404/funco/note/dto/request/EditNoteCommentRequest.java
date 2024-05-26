package com.found_404.funco.note.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EditNoteCommentRequest(
    @NotBlank
    String content
) {

}
