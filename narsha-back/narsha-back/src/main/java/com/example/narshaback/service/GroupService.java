package com.example.narshaback.service;

import com.example.narshaback.base.dto.group.CreateGroupDTO;
import com.example.narshaback.entity.UserEntity;

public interface GroupService {
    UserEntity createGroup(CreateGroupDTO createGroupDTO);
    String getUserGroupCode(String userId);
    String deleteGroup(String groupCode);
}
