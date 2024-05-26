package com.found_404.funco.note.dto.response;

import lombok.Builder;

@Builder
public record AddNoteResponse(
    Long noteId
) {

}
