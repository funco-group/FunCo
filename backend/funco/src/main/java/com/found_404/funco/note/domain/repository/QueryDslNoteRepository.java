package com.found_404.funco.note.domain.repository;

import com.found_404.funco.note.domain.Note;
import com.found_404.funco.note.dto.request.NotesFilterRequest;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface QueryDslNoteRepository {

    List<Note> getNotesWithFilter(NotesFilterRequest notesFilterRequest, Pageable pageable);
}
