package com.example.narshaback.repository;

import com.example.narshaback.base.projection.user.GetUserProfile;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository<Class Type, PK Type>
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findByGroupCode(String groupCode);
}
