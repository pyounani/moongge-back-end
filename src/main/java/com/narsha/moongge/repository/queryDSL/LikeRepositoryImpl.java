package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<LikeEntity> findLikesWithUserByPost(PostEntity post) {
        QLikeEntity like = QLikeEntity.likeEntity;
        QUserEntity user = QUserEntity.userEntity;

        return query.selectFrom(like)
                .join(like.user, user).fetchJoin()
                .where(like.post.eq(post))
                .fetch();
    }

    @Override
    public Boolean existsPostWithMinLikesByUser(UserEntity user, Long minLikes) {
        QLikeEntity like = QLikeEntity.likeEntity;
        QPostEntity post = QPostEntity.postEntity;

        return query.selectOne()
                .from(like)
                .join(like.post, post)
                .where(post.user.eq(user))
                .groupBy(like.post)
                .having(like.count().goe(minLikes))
                .fetchFirst() != null;
    }
}
