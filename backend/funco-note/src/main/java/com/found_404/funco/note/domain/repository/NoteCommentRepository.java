package com.found_404.funco.note.domain.repository;

import com.found_404.funco.note.domain.Note;
import com.found_404.funco.note.domain.NoteComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteCommentRepository extends JpaRepository<NoteComment, Long> {

    Long countByNote(Note note);

    List<NoteComment> findByNoteId(Long noteId);

    boolean existsByParentId(Long parentId);
}
