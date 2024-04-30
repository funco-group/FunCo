package com.found_404.funco.note.domain.repository;

import com.found_404.funco.note.domain.Image;
import com.found_404.funco.note.domain.Note;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByNote(Note note);


}
