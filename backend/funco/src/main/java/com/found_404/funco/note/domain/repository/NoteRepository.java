package com.found_404.funco.note.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.found_404.funco.note.domain.Note;

public interface NoteRepository extends JpaRepository<Note, Long>, QueryDslNoteRepository {

    @EntityGraph(attributePaths = {"member"})
    Optional<Note> findNoteById(Long id);
}
