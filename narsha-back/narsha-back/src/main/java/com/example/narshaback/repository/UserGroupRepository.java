package com.example.narshaback.repository;

import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.entity.User_Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<User_Group, Integer> {

    UserEntity findByUserId(String userId);
}
