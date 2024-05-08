package com.found_404.funco.note.dto.request;

import com.found_404.funco.note.dto.type.PostType;
import com.found_404.funco.note.dto.type.SearchType;
import com.found_404.funco.note.dto.type.SortedType;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public record NotesFilterRequest(

    Long memberId,

    PostType type,

    List<String> coin,

    SearchType search,

    String keyword,

    SortedType sorted

) {


}
