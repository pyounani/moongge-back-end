package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.CommentEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.QCommentEntity;
import com.narsha.moongge.entity.QUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<CommentEntity> findCommentsWithUserByPost(PostEntity post) {
        QCommentEntity comment = QCommentEntity.commentEntity;
        QUserEntity user = QUserEntity.userEntity;

        return query.selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.post.eq(post))
                .orderBy(comment.createAt.asc())
                .fetch();
    }

    @Override
    public Optional<CommentEntity> findTopCommentWithUserByPost(PostEntity post) {
        QCommentEntity comment = QCommentEntity.commentEntity;
        QUserEntity user = QUserEntity.userEntity;

        return Optional.ofNullable(query.selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.post.eq(post))
                .orderBy(comment.createAt.desc())
                .fetchFirst());
    }
}
