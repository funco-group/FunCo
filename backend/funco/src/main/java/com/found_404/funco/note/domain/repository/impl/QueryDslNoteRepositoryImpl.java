package com.found_404.funco.note.domain.repository.impl;

import static com.found_404.funco.note.domain.QNote.note;
import static com.found_404.funco.note.domain.QNoteLike.noteLike;

import com.found_404.funco.member.domain.Member;
import com.found_404.funco.note.domain.Note;

import com.found_404.funco.note.domain.repository.QueryDslNoteRepository;
import com.found_404.funco.note.dto.request.NotesFilterRequest;
import com.found_404.funco.note.dto.type.PostType;
import com.found_404.funco.note.dto.type.SearchType;
import com.found_404.funco.note.dto.type.SortedType;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QueryDslNoteRepositoryImpl implements QueryDslNoteRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Note> getNotesWithFilter(NotesFilterRequest notesFilterRequest) {
//        if (Objects.isNull(notesFilterRequest)) {
//            return jpaQueryFactory
//                .selectFrom(note)
//                .fetch();
//        }

        return jpaQueryFactory
            .selectFrom(note)
            .where(postTypeFilter(notesFilterRequest.id(), notesFilterRequest.type()),
                    coinFilter(notesFilterRequest.coin()),
                    searchFilter(notesFilterRequest.search(), notesFilterRequest.keyword()))
            .orderBy(sortedBy(notesFilterRequest.sorted()))
//            .limit(pageable.getPageSize())
//            .offset(pageable.getOffset())
            .fetch();
    }

    private BooleanExpression postTypeFilter(Long id, PostType type){
        if (Objects.isNull(type)) {
            return null;
        }
        return switch (type) {
            case ALL -> null;
            case MY -> Objects.isNull(id) ? Expressions.asBoolean(false).isTrue() : note.member.id.eq(id);
            case LIKE -> Objects.isNull(id) ? Expressions.asBoolean(false).isTrue() :
                JPAExpressions.selectOne()
                .from(noteLike)
                .where(noteLike.note.id.eq(note.id)
                    .and(noteLike.member.id.eq(id)))
                .exists();
        };

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
            case WRITER -> note.member.nickname.contains(keyword);
        };

    }

    public OrderSpecifier<?> sortedBy(SortedType sorted) {
        if (Objects.isNull(sorted)) {
            return note.createdAt.desc();
        }

        if (SortedType.RECOMMENDED.name().equals(sorted.name())) {
            JPQLQuery<Long> likeCount = JPAExpressions
                .select(noteLike.count())
                .from(noteLike)
                .where(noteLike.note.id.eq(note.id))
                .groupBy(noteLike.note.id);
            return new OrderSpecifier<>(Order.DESC, likeCount);
        }

        return note.createdAt.desc();
    }

}
