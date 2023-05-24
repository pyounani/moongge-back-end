package com.example.narshaback.repository;

import com.example.narshaback.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    ProfileEntity findByProfile(Integer userGroupId); // 해당 유저-그룹 객체 id를 가진 프로필
}
