package com.example.narshaback.service;

import com.example.narshaback.base.dto.group.CreateGroupDTO;
import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.projection.user.GetUserInGroup;
import com.example.narshaback.base.projection.user_group.GetJoinGroupList;
import com.example.narshaback.entity.UserEntity;

import java.util.List;

public interface GroupService {
    String createGroup(CreateGroupDTO createGroupDTO);
    String getUserGroupCode(String userId);
}
