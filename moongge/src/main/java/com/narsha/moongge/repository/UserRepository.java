package com.narsha.moongge.repository;

import com.narsha.moongge.base.projection.user.GetUser;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.group.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// JpaRepository<Class Type, PK Type>
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> deleteByGroupCode(GroupEntity groupCode);
    List<GetUser> findByGroupCode(GroupEntity GroupId);

    List<GetUser> findByGroupCodeAndUserIdNotLike(GroupEntity GroupId, String userId);

//    Optional<UserEntity> findByGroupCode(String groupCode);
}
