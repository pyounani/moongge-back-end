package com.example.narshaback.repository;

import com.example.narshaback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Class Type, PK Type>
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserId(String userId);

}
