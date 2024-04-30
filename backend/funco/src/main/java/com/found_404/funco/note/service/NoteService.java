package com.found_404.funco.note.service;

import static com.found_404.funco.note.exception.NoteErrorCode.NOT_FOUND_NOTE;

import com.found_404.funco.member.domain.Member;
import com.found_404.funco.member.domain.repository.MemberRepository;
import com.found_404.funco.note.domain.Note;
import com.found_404.funco.note.domain.repository.ImageRepository;
import com.found_404.funco.note.domain.repository.NoteCommentRepository;
import com.found_404.funco.note.domain.repository.NoteLikeRepository;
import com.found_404.funco.note.domain.repository.NoteRepository;
import com.found_404.funco.note.dto.request.NoteRequest;
import com.found_404.funco.note.dto.request.NotesFilterRequest;
import com.found_404.funco.note.dto.response.NoteResponse;
import com.found_404.funco.note.dto.response.NotesResponse;
import com.found_404.funco.note.dto.type.PostType;
import com.found_404.funco.note.exception.NoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final ImageRepository imageRepository;
    private final NoteCommentRepository noteCommentRepository;
    private final NoteLikeRepository noteLikeRepository;
    private final MemberRepository memberRepository;


    public List<NotesResponse> getNotes(Member member, NotesFilterRequest notesFilterRequest) {
        if (Objects.isNull(member)) {
            if (Objects.nonNull(notesFilterRequest.type())
                && (PostType.MY.name().equals(notesFilterRequest.type().name())
                || PostType.LIKE.name().equals(notesFilterRequest.type().name()))) {
                return null;
            }
        }

        return noteRepository.getNotesWithFilter(member, notesFilterRequest)
            .stream().map(note ->  NotesResponse.builder()
                .noteId(note.getId())
                .nickname(note.getMember().getNickname())
                .profileImage(note.getMember().getProfileUrl())
                .thumbnail(null) // 수정!!
                .title(note.getTitle())
                .content(note.getContent())
                .coinName(note.getTicker())
                .writeDate(note.getCreatedAt())
                .likeCount(noteLikeRepository.countByNote(note))
                .liked(false)  // 수정!!
                .commmentCount(noteCommentRepository.countByNote(note))
                .build())
            .toList();

    }

    public NoteResponse getNote(Long noteId) {
         Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));
         Long likeCount = noteLikeRepository.countByNote(note);
         Long commentCount = noteCommentRepository.countByNote(note);

        return NoteResponse.builder()
            .noteId(note.getId())
            .nickname(note.getMember().getNickname())
            .title(note.getTitle())
            .content(note.getContent())
            .coinName(note.getTicker())
            .likeCount(likeCount)
            .liked(false) // 수정해야 됨!!!
            .commentCount(commentCount)
            .build();
    }


    public void addNote(Member member, NoteRequest request) {
        if (Objects.nonNull(member)) {
            Member tempMember = memberRepository.findById(1L).orElseThrow();
            noteRepository.save(Note.builder()
                .member(tempMember)
                .title(request.title())
                .content(request.content())
                .ticker(request.ticker())
                .build());
        }

    }

    public void removeNote(Long memberId, Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));
        if (note.getMember().getId().equals(memberId)) {
            noteRepository.delete(note);
        }
    }

    @Transactional
    public void editNote(Member member, Long noteId, NoteRequest request) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));
        if (Objects.nonNull(member) && note.getMember().getId().equals(member.getId())) {
            note.editNote(request.title(), request.content(), request.ticker());
        }
    }
}
