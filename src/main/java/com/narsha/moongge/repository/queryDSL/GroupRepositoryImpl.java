package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.QUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public void clearGroupForUsers(GroupEntity group) {
        QUserEntity user = QUserEntity.userEntity;

        query.update(user)
                .where(user.group.eq(group))
                .set(user.group, (GroupEntity) null)
                .execute();

    }
}
