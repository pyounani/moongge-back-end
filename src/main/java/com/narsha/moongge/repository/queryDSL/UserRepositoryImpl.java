package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.QGroupEntity;
import com.narsha.moongge.entity.QUserEntity;
import com.narsha.moongge.entity.UserEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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


    @Override
    public boolean existsByGroupCodeAndUserId(String groupCode, String userId) {
        QUserEntity user = QUserEntity.userEntity;
        QGroupEntity group = QGroupEntity.groupEntity;

        BooleanExpression condition = group.groupCode.eq(groupCode)
                .and(user.userId.eq(userId));

        return query
                .selectOne()
                .from(user)
                .join(user.group, group)
                .where(condition)
                .fetchFirst() != null;
    }

    @Override
    public List<UserEntity> getUsersInGroupExcept(String groupCode, String userId) {
        QUserEntity user = QUserEntity.userEntity;
        QGroupEntity group = QGroupEntity.groupEntity;

        return query
                .selectFrom(user)
                .join(user.group, group)
                .where(group.groupCode.eq(groupCode)
                        .and(user.userId.ne(userId)))
                .fetch();

    }

    @Override
    public Optional<UserEntity> findUserWithGroup(String userId) {
        QUserEntity user = QUserEntity.userEntity;
        QGroupEntity group = QGroupEntity.groupEntity;

        return Optional.ofNullable(
                query.selectFrom(user)
                        .join(user.group, group)
                        .fetchJoin() // 그룹 정보도 함께 가져오기 위해 fetchJoin 사용
                        .where(user.userId.eq(userId))
                        .fetchOne()
        );
    }
}
