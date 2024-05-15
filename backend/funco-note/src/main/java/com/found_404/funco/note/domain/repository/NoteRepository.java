package com.found_404.funco.note.domain.repository;

import com.found_404.funco.note.domain.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long>, QueryDslNoteRepository {

    @EntityGraph(attributePaths = {"member"})
    Optional<Note> findNoteById(Long id);
}
