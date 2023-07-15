package com.example.narshaback.service;

import com.example.narshaback.base.dto.group.CreateGroupDTO;
import com.example.narshaback.entity.UserEntity;

import java.util.Optional;

public interface GroupService {
    UserEntity createGroup(CreateGroupDTO createGroupDTO);
    String getUserGroupCode(String userId);
}
