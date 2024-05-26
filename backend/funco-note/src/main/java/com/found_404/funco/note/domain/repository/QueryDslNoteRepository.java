package com.found_404.funco.note.domain.repository;

import com.found_404.funco.note.domain.Note;
import com.found_404.funco.note.dto.request.NotesFilterRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryDslNoteRepository {

    List<Note> getNotesWithFilter(Long memberId, NotesFilterRequest notesFilterRequest, Pageable pageable);

    boolean deleteNoteWithComments(Long noteId);

}
