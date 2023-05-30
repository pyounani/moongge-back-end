package com.example.narshaback.repository;

import com.example.narshaback.entity.ProfileEntity;
import com.example.narshaback.entity.User_Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    ProfileEntity findByUserGroup(User_Group userGroupId); // 해당 유저-그룹 객체 id를 가진 프로필
    Optional<ProfileEntity> findById(Integer ProfileId); //프로필 고유 id
}
