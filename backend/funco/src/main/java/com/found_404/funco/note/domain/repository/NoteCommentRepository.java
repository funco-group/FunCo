package com.found_404.funco.note.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.found_404.funco.note.domain.NoteComment;

public interface NoteCommentRepository extends CrudRepository<NoteComment, Long> {
}
