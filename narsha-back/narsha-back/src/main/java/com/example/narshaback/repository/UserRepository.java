package com.example.narshaback.repository;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

// JpaRepository<Class Type, PK Type>
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findByGroupCode(String groupCode);

    Optional<UserEntity> deleteByGroupCode(GroupEntity groupCode);
}
