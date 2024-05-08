package com.found_404.funco.note.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
