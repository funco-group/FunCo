package com.found_404.funco.note.domain.repository.impl;

import com.found_404.funco.note.domain.Note;
import com.found_404.funco.note.domain.repository.QueryDslNoteRepository;
import com.found_404.funco.note.dto.request.NotesFilterRequest;
import com.found_404.funco.note.dto.type.SearchType;
import com.found_404.funco.note.dto.type.SortedType;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

import static com.found_404.funco.note.domain.QNote.note;
import static com.found_404.funco.note.domain.QNoteComment.noteComment;
import static com.found_404.funco.note.domain.QNoteLike.noteLike;

@RequiredArgsConstructor
public class QueryDslNoteRepositoryImpl implements QueryDslNoteRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Note> getNotesWithFilter(Long memberId, NotesFilterRequest notesFilterRequest, Pageable pageable) {
        JPAQuery<Note> query = switch (notesFilterRequest.type()) {
            case ALL -> allPost();
            case MY -> myPost(memberId);
            case LIKE -> likePost(memberId);
        };

        return query
            .where(coinFilter(notesFilterRequest.coin()),
                            searchFilter(notesFilterRequest.search(),
                            notesFilterRequest.keyword()))
            .orderBy(sortedBy(notesFilterRequest.sorted()))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

    }

    @Override
    public boolean deleteNoteWithComments(Long noteId) {
        jpaQueryFactory
                .delete(noteComment)
                .where(noteComment.note.id.eq(noteId))
                .execute();

        return jpaQueryFactory.delete(note)
                .where(note.id.eq(noteId))
                .execute() > 0;
    }

    private JPAQuery<Note> allPost() {
        return jpaQueryFactory
            .selectFrom(note);
    }

    private JPAQuery<Note> myPost(Long memberId) {
        return jpaQueryFactory
            .selectFrom(note)
            .where(note.memberId.eq(memberId));
    }

    private JPAQuery<Note> likePost(Long memberId) {
        return jpaQueryFactory
            .selectFrom(note)
            .from(noteLike)
            .where(note.id.eq(noteLike.note.id)
                .and(noteLike.memberId.eq(memberId)));
    }

    private BooleanExpression coinFilter(List<String> coin) {
        return Objects.isNull(coin) || coin.isEmpty() ? null : note.ticker.in(coin);
    }

    private BooleanExpression searchFilter(SearchType search, String keyword) {
        if (Objects.isNull(search) || Objects.isNull(keyword)) {
            return null;
        }

        return switch (search) {
            case TITLE -> note.title.contains(keyword);
            case CONTENT ->  note.content.contains(keyword);
            //case WRITER -> note.member.nickname.contains(keyword);
        };

    }

    public OrderSpecifier<?> sortedBy(SortedType sorted) {
        if (Objects.isNull(sorted)) {
            return note.id.desc();
        }

        if (SortedType.RECOMMENDED.name().equals(sorted.name())) {
            JPQLQuery<Long> likeCount = JPAExpressions
                .select(noteLike.count())
                .from(noteLike)
                .where(noteLike.note.id.eq(note.id))
                .groupBy(noteLike.note.id);
            return new OrderSpecifier<>(Order.DESC, likeCount);
        }

        return note.id.desc();
    }

}
