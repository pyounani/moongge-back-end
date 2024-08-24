package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.QUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<String> findBadgeListByUserId(String userId) {
        QUserEntity user = QUserEntity.userEntity;

        return Optional.ofNullable(
                query.select(user.badgeList)
                        .from(user)
                        .where(user.userId.eq(userId))
                        .fetchOne()
        );
    }
}
