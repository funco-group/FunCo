package com.found_404.funco.note.dto.request;

import com.found_404.funco.note.dto.type.PostType;
import com.found_404.funco.note.dto.type.SearchType;
import com.found_404.funco.note.dto.type.SortedType;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record NotesFilterRequest(

    Long memberId,

    @NotNull(message = "type이 없습니다.")
    PostType type,

    List<String> coin,

    SearchType search,

    String keyword,

    SortedType sorted

) {


}
