package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.QPostEntity;
import com.narsha.moongge.entity.QUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<PostEntity> findByPostIdAndGroupWithWriter(Integer postId, String groupCode) {
        QPostEntity post = QPostEntity.postEntity;
        QUserEntity user = QUserEntity.userEntity;

        return Optional.ofNullable(query.selectFrom(post)
                .join(post.user, user).fetchJoin()
                .where(post.postId.eq(postId)
                        .and(post.group.groupCode.eq(groupCode)))
                .fetchOne());
    }


}
