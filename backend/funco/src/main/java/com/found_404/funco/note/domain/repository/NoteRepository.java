package com.found_404.funco.note.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.found_404.funco.note.domain.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
