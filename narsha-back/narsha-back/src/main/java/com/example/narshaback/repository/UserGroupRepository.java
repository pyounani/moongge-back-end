package com.example.narshaback.repository;

import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.projection.user.GetUserInGroup;
import com.example.narshaback.projection.user_group.GetJoinGroupList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<User_Group, Integer> {
    List<GetUserInGroup> findByGroup(GroupEntity group);
    Optional<User_Group> findById(Integer id);
    List<GetJoinGroupList> findByUser(UserEntity user);
}
