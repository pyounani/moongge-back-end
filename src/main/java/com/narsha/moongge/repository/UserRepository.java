package com.narsha.moongge.repository;

import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.repository.queryDSL.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// JpaRepository<Class Type, PK Type>
public interface UserRepository extends JpaRepository<UserEntity, String>, UserRepositoryCustom {
    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByUserIdAndGroup(String userId, GroupEntity group);
    List<UserEntity> findByGroupAndUserIdNotLike(GroupEntity group, String userId);
    List<UserEntity> findByGroup(GroupEntity group);
    boolean existsByUserId(String userId);
}
