package com.found_404.funco.note.domain.repository;

import com.found_404.funco.note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long>, QueryDslNoteRepository {

}
