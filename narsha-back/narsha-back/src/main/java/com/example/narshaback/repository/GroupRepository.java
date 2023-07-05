package com.example.narshaback.repository;

import com.example.narshaback.base.projection.user.GetUserInGroup;
import com.example.narshaback.base.projection.user_group.GetJoinGroupList;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity,String> {
    Optional<GroupEntity> findByGroupCode(String groupCode);
}
