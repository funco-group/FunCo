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
import com.found_404.funco.note.exception.NoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final ImageRepository imageRepository;
    private final NoteCommentRepository noteCommentRepository;
    private final NoteLikeRepository noteLikeRepository;
    private final MemberRepository memberRepository;


    public List<NotesResponse> getNotes(Long memberId, NotesFilterRequest notesFilterRequest) {
        if (Objects.isNull(memberId)) {
            if ("MY".equals(notesFilterRequest.type().name()) || "LIKE".equals(notesFilterRequest.type().name())) {
                return null;
            }
        }

        return noteRepository.getNotesWithFilter(memberId, notesFilterRequest)
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
//        Member tempMember = memberRepository.findById(1L).orElseThrow();
        noteRepository.save(Note.builder()
                .member(member)
                .title(request.title())
                .content(request.content())
                .ticker(request.ticker())
                .build());
    }

    public void removeNote(Long memberId, Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));
        if (note.getMember().getId().equals(memberId)) {
            noteRepository.delete(note);
        }
    }
}
