package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<PostEntity> findByPostIdAndGroupWithWriter(Integer postId, GroupEntity group) {
        QPostEntity post = QPostEntity.postEntity;
        QUserEntity user = QUserEntity.userEntity;

        return Optional.ofNullable(query.selectFrom(post)
                .join(post.user, user).fetchJoin()
                .where(post.postId.eq(postId)
                        .and(post.group.eq(group)))  // groupCode 대신 group 객체 자체로 비교
                .fetchOne());
    }

    @Override
    public List<PostEntity> getMainPost(String userId, GroupEntity group, LocalDateTime startTime, LocalDateTime endTime) {
        QPostEntity post = QPostEntity.postEntity;
        QUserEntity user = QUserEntity.userEntity;
        QLikeEntity like = QLikeEntity.likeEntity;

        return query.select(post)
                .from(post)
                .join(post.user, user).fetchJoin()
                .leftJoin(like).on(like.post.eq(post).and(like.user.userId.eq(userId)))
                .where(post.group.eq(group)
                        .and(post.createAt.between(startTime, endTime))
                        .and(post.user.userId.ne(userId))
                        .and(like.isNull()))
                .orderBy(post.createAt.desc())
                .fetch();
    }


}
