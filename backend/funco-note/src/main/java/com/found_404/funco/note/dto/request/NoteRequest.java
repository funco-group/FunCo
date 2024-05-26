package com.found_404.funco.note.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NoteRequest (

    @NotBlank
    String title,

    @NotBlank
    String content,

    @NotBlank
    String ticker,

    @NotBlank
    String thumbnailImage
    )
{
}
