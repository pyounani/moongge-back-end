package com.example.narshaback.repository;

import com.example.narshaback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // 1. 유저 회원가입
    void register(UserEntity user);
}
