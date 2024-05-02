package com.found_404.funco.note.service;

import static com.found_404.funco.note.exception.NoteErrorCode.NOT_FOUND_NOTE;

import com.found_404.funco.badge.domain.Badge;
import com.found_404.funco.badge.domain.repository.BadgeRepository;
import com.found_404.funco.member.domain.Member;
import com.found_404.funco.member.domain.repository.MemberRepository;
import com.found_404.funco.note.domain.Note;
import com.found_404.funco.note.domain.NoteComment;
import com.found_404.funco.note.domain.repository.ImageRepository;
import com.found_404.funco.note.domain.repository.NoteCommentRepository;
import com.found_404.funco.note.domain.repository.NoteLikeRepository;
import com.found_404.funco.note.domain.repository.NoteRepository;
import com.found_404.funco.note.dto.request.NoteRequest;
import com.found_404.funco.note.dto.request.NotesFilterRequest;
import com.found_404.funco.note.dto.response.CommentsResponse;
import com.found_404.funco.note.dto.response.NoteMemberResponse;
import com.found_404.funco.note.dto.response.NoteResponse;
import com.found_404.funco.note.dto.response.NotesResponse;
import com.found_404.funco.note.exception.NoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final BadgeRepository badgeRepository;


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

    public List<CommentsResponse> getComments(Long noteId) {
        List<NoteComment> parentComments = noteCommentRepository.findByNoteIdAndParentIdIsNull(noteId);
        List<CommentsResponse> commentsResponses = new ArrayList<>();

        for (NoteComment parentComment : parentComments) {
            List<NoteComment> childComments = noteCommentRepository.findByParentId(parentComment.getId());

            commentsResponses.add(
                CommentsResponse.builder()
                    .commentId(parentComment.getId())
                    .member(NoteMemberResponse.builder()
                        .memberId(parentComment.getMember().getId())
                        .nickname(parentComment.getMember().getNickname())
                        .profileUrl(parentComment.getMember().getProfileUrl())
                        .badgeId(badgeRepository.findByMember(parentComment.getMember()).getId())
                        .build())
                    .childComments(childComments
                        .stream().map(childComment -> CommentsResponse.builder()
                            .commentId(childComment.getId())
                            .member(NoteMemberResponse.builder()
                                .memberId(childComment.getMember().getId())
                                .nickname(childComment.getMember().getNickname())
                                .profileUrl(childComment.getMember().getProfileUrl())
                                .badgeId(badgeRepository.findByMember(childComment.getMember()).getId())
                                .build())
                            .childComments(null)
                            .content(childComment.getContent())
                            .date(childComment.getCreatedAt())
                            .build())
                        .toList())
                    .content(parentComment.getContent())
                    .date(parentComment.getCreatedAt())
                    .build()
            );
        }

        return commentsResponses;
    }
}
