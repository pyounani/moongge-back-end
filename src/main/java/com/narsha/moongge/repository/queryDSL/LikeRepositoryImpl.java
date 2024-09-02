package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.LikeEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.QLikeEntity;
import com.narsha.moongge.entity.QUserEntity;
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
}
