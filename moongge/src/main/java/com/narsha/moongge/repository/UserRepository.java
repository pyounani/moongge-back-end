package com.narsha.moongge.repository;

import com.narsha.moongge.base.projection.user.GetUser;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// JpaRepository<Class Type, PK Type>
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserId(String userId);
    List<GetUser> findByGroupAndUserIdNotLike(GroupEntity group, String userId);
    List<UserEntity> findByGroup(GroupEntity group);
}
