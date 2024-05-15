package com.found_404.funco.note.domain.repository;

import com.found_404.funco.note.domain.Note;
import com.found_404.funco.note.domain.NoteLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteLikeRepository extends JpaRepository<NoteLike, Long> {

    Long countByNote(Note note);

    Optional<NoteLike> findByMemberIdAndNote(Long memberId, Note note);

}
