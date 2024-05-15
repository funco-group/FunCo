package com.found_404.funco.note.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.found_404.funco.feignClient.dto.SimpleMember;
import com.found_404.funco.feignClient.service.MemberService;
import com.found_404.funco.note.domain.Note;
import com.found_404.funco.note.domain.NoteComment;
import com.found_404.funco.note.domain.NoteLike;
import com.found_404.funco.note.domain.repository.NoteCommentRepository;
import com.found_404.funco.note.domain.repository.NoteLikeRepository;
import com.found_404.funco.note.domain.repository.NoteRepository;
import com.found_404.funco.note.dto.request.CommentRequest;
import com.found_404.funco.note.dto.request.NoteRequest;
import com.found_404.funco.note.dto.request.NotesFilterRequest;
import com.found_404.funco.note.dto.response.*;
import com.found_404.funco.note.dto.type.PostType;
import com.found_404.funco.note.exception.NoteException;
import com.found_404.funco.note.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.found_404.funco.note.exception.NoteErrorCode.*;
import static com.found_404.funco.note.exception.S3ErrorCode.PUT_OBJECT_EXCEPTION;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteService {

    private final static int THUMBNAIL_CONTENT_LENGTH = 120;

    private final NoteRepository noteRepository;
    private final NoteCommentRepository noteCommentRepository;
    private final NoteLikeRepository noteLikeRepository;

    private final MemberService memberService;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    public List<NotesResponse> getNotes(NotesFilterRequest notesFilterRequest, Pageable pageable) {
        if ((notesFilterRequest.type() == PostType.MY || notesFilterRequest.type() == PostType.LIKE)
                && Objects.isNull(notesFilterRequest.memberId())) {
            throw new NoteException(INVALID_FILTER);
        }
        List<Note> notes = noteRepository.getNotesWithFilter(notesFilterRequest, pageable);
        Map<Long, SimpleMember> simpleMembers = memberService.getSimpleMember(notes.stream().map(Note::getMemberId).toList());

        return notes
            .stream().map(note ->  NotesResponse.builder()
                .noteId(note.getId())
                .member(simpleMembers.get(note.getMemberId()))
                .thumbnailImage(note.getThumbnailImage())
                .thumbnailContent(note.getThumbnailContent())
                .title(note.getTitle())
                .ticker(note.getTicker())
                .writeDate(note.getCreatedAt())
                .likeCount(noteLikeRepository.countByNote(note))
                .liked(false)  // 수정!!
                .commentCount(noteCommentRepository.countByNote(note))
                .build())
            .toList();

    }

    public NoteResponse getNote(Long noteId) {
        Note note = noteRepository.findNoteById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));
        Long likeCount = noteLikeRepository.countByNote(note);
        Long commentCount = noteCommentRepository.countByNote(note);

        Map<Long, SimpleMember> simpleMembers = memberService.getSimpleMember(note.getMemberId());

        return NoteResponse.builder()
                .noteId(note.getId())
                .member(simpleMembers.get(note.getMemberId()))
                .title(note.getTitle())
                .content(note.getContent())
                .ticker(note.getTicker())
                .likeCount(likeCount)
                .liked(false) // 수정해야 됨!!!
                .commentCount(commentCount)
                .build();
    }


    public AddNoteResponse addNote(Long memberId, NoteRequest request) {
        Note note = noteRepository.save(Note.builder()
                .memberId(memberId)
                .title(request.title())
                .content(request.content())
                .ticker(request.ticker())
                .thumbnailImage(request.thumbnailImage())
                .thumbnailContent(getThumbnailContent(request.content(), THUMBNAIL_CONTENT_LENGTH))
                .build());

        return AddNoteResponse.builder()
            .noteId(note.getId())
            .build();
    }


    public void removeNote(Long memberId, Long noteId) {
        Note note = noteRepository.findNoteById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));
        checkAuthorization(memberId, note);
        noteRepository.delete(note);
    }

    private static void checkAuthorization(Long memberId, Note note) {
        if (!note.getMemberId().equals(memberId)) {
           throw new NoteException(UNAUTHORIZED);
        }
    }

    public void editNote(Long memberId, Long noteId, NoteRequest request) {
        Note note = noteRepository.findNoteById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));
        checkAuthorization(memberId, note);
        note.editNote(request.title(), request.content(), request.ticker(), request.thumbnailImage(), getThumbnailContent(request.content(), THUMBNAIL_CONTENT_LENGTH));
    }

    public List<CommentsResponse> getComments(Long noteId) {
        List<NoteComment> comments = noteCommentRepository.findByNoteId(noteId);

        Map<Long, SimpleMember> simpleMembers = memberService.getSimpleMember(comments.stream()
                .map(NoteComment::getMemberId)
                .collect(Collectors.toSet())
                .stream().toList());

        List<CommentsResponse> commentsResponses = new ArrayList<>();
        Map<Long, List<NoteComment>> childComments = new HashMap<>();
        for (NoteComment comment : comments) {
            long key = Objects.isNull(comment.getParentId()) ? 0 : comment.getParentId();
            childComments.putIfAbsent(key, new ArrayList<>());
            childComments.get(key).add(comment);
        }

        for (NoteComment comment : childComments.getOrDefault(0L, Collections.emptyList())) {
            commentsResponses.add(
                getCommentsResponse(childComments, comment, simpleMembers)
            );
        }

        return commentsResponses;
    }

    private CommentsResponse getCommentsResponse(Map<Long, List<NoteComment>> childComments, NoteComment comment, Map<Long, SimpleMember> simpleMembers) {

        return CommentsResponse.builder()
            .commentId(comment.getId())
            .member(simpleMembers.get(comment.getMemberId()))
            .content(comment.getContent())
            .date(comment.getCreatedAt())
            .childComments(childComments.getOrDefault(comment.getId(), Collections.emptyList())
                .stream().map(childComment -> getCommentsResponse(childComments, childComment, simpleMembers))
                .toList())
            .build();
    }

    public void addComment(Long memberId, Long noteId, CommentRequest request) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));

        noteCommentRepository.save(NoteComment.builder()
                .memberId(memberId)
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

    public String getThumbnailContent(String content, int length) {
        Document doc = Jsoup.parse(content);
        doc.select("img").remove();
        return doc.text().substring(length);
    }

    public void addNoteLike(Long memberId, Long noteId) {
        Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new NoteException(NOT_FOUND_NOTE));

        Optional<NoteLike> noteLike = noteLikeRepository.findByMemberIdAndNote(memberId, note);

        if (noteLike.isPresent()) {
            noteLikeRepository.delete(noteLike.get());
        }
        else {
            noteLikeRepository.save(NoteLike.builder()
                    .memberId(memberId)
                    .note(note)
                    .build());
        }

    }
}