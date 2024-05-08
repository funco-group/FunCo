package com.found_404.funco.note.service;

import static com.found_404.funco.member.exception.MemberErrorCode.INVALID_MEMBER;
import static com.found_404.funco.member.exception.MemberErrorCode.NOT_FOUND_MEMBER;
import static com.found_404.funco.note.exception.NoteErrorCode.NOT_FOUND_NOTE;
import static com.found_404.funco.note.exception.S3ErrorCode.PUT_OBJECT_EXCEPTION;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.found_404.funco.badge.domain.HoldingBadge;
import com.found_404.funco.badge.domain.repository.HoldingBadgeRepository;
import com.found_404.funco.member.domain.Member;

import com.found_404.funco.member.domain.repository.MemberRepository;
import com.found_404.funco.member.exception.MemberException;
import com.found_404.funco.note.domain.Note;
import com.found_404.funco.note.domain.NoteComment;

import com.found_404.funco.note.domain.repository.NoteCommentRepository;
import com.found_404.funco.note.domain.repository.NoteLikeRepository;
import com.found_404.funco.note.domain.repository.NoteRepository;
import com.found_404.funco.note.dto.request.CommentRequest;
import com.found_404.funco.note.dto.request.NoteRequest;
import com.found_404.funco.note.dto.request.NotesFilterRequest;
import com.found_404.funco.note.dto.response.AddNoteResponse;
import com.found_404.funco.note.dto.response.CommentsResponse;
import com.found_404.funco.note.dto.response.ImageResponse;
import com.found_404.funco.note.dto.response.NoteMemberResponse;
import com.found_404.funco.note.dto.response.NoteResponse;
import com.found_404.funco.note.dto.response.NotesResponse;
import com.found_404.funco.note.exception.NoteException;
import com.found_404.funco.note.exception.S3Exception;
import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteCommentRepository noteCommentRepository;
    private final NoteLikeRepository noteLikeRepository;
    private final HoldingBadgeRepository holdingBadgeRepository;
    private final MemberRepository memberRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    public List<NotesResponse> getNotes(NotesFilterRequest notesFilterRequest) {
        String htmlContent = "<h2>변경사항</h2><ul><li><p>로그인 모달</p></li><li><p>거래소</p><ul><li><p>네브바 수정</p><ul><li><p>클릭 시 일반 거래소, 선물 거래소 드랍 다운 나옴</p></li><li><p>디폴트</p></li></ul></li><li><p>선물 거래소 페이지 추가</p><ul><li><p>포지션 현황</p><ul><li><p>주문금액</p></li><li><p>변동폭</p></li><li><p>수익률</p></li><li><p>손익</p></li><li><p>레버리지(내가 주문한 금액의 몇 배로 움직이는지)</p></li><li><p>정산금</p></li><li><p><br></p></li></ul></li></ul></li></ul><p><img src=\"/image/hanni-hat.gif\" alt=\"db2bbf150bc7ff4b99271298af0ccbda35dbdd57222086e7176210f43066aba0.jpg\" contenteditable=\"false\"><br></p></li></ul>";
//        Document doc = Jsoup.parse(htmlContent);
//        doc.select("img").remove();  // 모든 이미지 태그 제거
//        return doc.text();  // 텍스트 내용만 추출



        return noteRepository.getNotesWithFilter(notesFilterRequest)
            .stream().map(note ->  NotesResponse.builder()
                .noteId(note.getId())
                .nickname(note.getMember().getNickname())
                .profileImage(note.getMember().getProfileUrl())
                .thumbnail(note.getThumbnail())
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


    public AddNoteResponse addNote(Long memberId, NoteRequest request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        Note note = noteRepository.save(Note.builder()
            .member(member)
            .title(request.title())
            .content(request.content())
            .ticker(request.ticker())
            .thumbnail(request.thumbnail())
            .build());

        return AddNoteResponse.builder()
            .noteId(note.getId())
            .build();
    }

    public void removeNote(Long memberId, Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));
        if (!note.getMember().getId().equals(memberId)) {
           throw new MemberException(INVALID_MEMBER);
        }
        noteRepository.delete(note);
    }

    @Transactional
    public void editNote(Long memberId, Long noteId, NoteRequest request) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));
        if (!note.getMember().getId().equals(memberId)) {
            throw new MemberException(INVALID_MEMBER);
        }
        note.editNote(request.title(), request.content(), request.ticker());
    }

    public List<CommentsResponse> getComments(Long noteId) {
        List<NoteComment> comments = noteCommentRepository.findByNoteId(noteId);
        List<CommentsResponse> commentsResponses = new ArrayList<>();

        Map<Long, List<NoteComment>> childComments = new HashMap<>();
        for (NoteComment comment : comments) {
            long key = Objects.isNull(comment.getParentId()) ? 0 : comment.getParentId();
            childComments.putIfAbsent(key, new ArrayList<>());
            childComments.get(key).add(comment);
        }


        for (NoteComment comment : childComments.getOrDefault(0L, Collections.emptyList())) {
            commentsResponses.add(
                getCommentsResponse(childComments, comment)
            );
        }

        return commentsResponses;
    }

    private CommentsResponse getCommentsResponse(Map<Long, List<NoteComment>> childComments,
        NoteComment comment) {
        return CommentsResponse.builder()
            .commentId(comment.getId())
            .member(NoteMemberResponse.builder()
                .memberId(comment.getMember().getId())
                .nickname(comment.getMember().getNickname())
                .profileUrl(comment.getMember().getProfileUrl())
                .badgeId(getHoldingBadge(comment))
                .build())
            .content(comment.getContent())
            .date(comment.getCreatedAt())
            .childComments(childComments.getOrDefault(comment.getId(), Collections.emptyList())
                .stream().map(childComment -> getCommentsResponse(childComments, childComment))
                .toList())
            .build();
    }

    public Long getHoldingBadge(NoteComment comment) {
        Optional<HoldingBadge> optionalHoldingBadge = holdingBadgeRepository.findByMember(comment.getMember());
        return optionalHoldingBadge.isPresent() ? optionalHoldingBadge.get().getId() : -1L;
    }

    public void addComment(Long memberId, Long noteId, CommentRequest request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));

        noteCommentRepository.save(NoteComment.builder()
                .member(member)
                .note(note)
                .parentId(request.parentCommentId())
                .content(request.content())
            .build());
    }

    public ImageResponse uploadImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename(); //원본 파일 명
        String extension = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf(".")); //확장자 명

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extension);

        try (
            InputStream is = file.getInputStream();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(IOUtils.toByteArray(is))) {

            metadata.setContentLength(IOUtils.toByteArray(is).length);
            PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e){
            log.error("s3 bucket upload error msg : {}", e.getMessage());
            throw new S3Exception(PUT_OBJECT_EXCEPTION);
        }
        return ImageResponse.builder()
            .url(amazonS3.getUrl(bucketName, s3FileName).toString())
            .build();
    }
}
