package com.found_404.funco.note.domain.repository;

import com.found_404.funco.note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import com.found_404.funco.note.domain.NoteComment;

public interface NoteCommentRepository extends JpaRepository<NoteComment, Long> {

    Long countByNote(Note note);
}
