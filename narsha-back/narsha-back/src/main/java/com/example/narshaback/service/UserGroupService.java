package com.example.narshaback.service;

import com.example.narshaback.dto.JoinGroupDTO;
import com.example.narshaback.projection.GetUserInGroup;

import java.util.List;

public interface UserGroupService {
    Boolean joinUser(JoinGroupDTO joinGroupDTO);
    List<GetUserInGroup> getUserListInGroup(String groupId);
}
