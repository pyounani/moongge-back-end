package com.example.narshaback.service;

import com.example.narshaback.dto.group.JoinGroupDTO;
import com.example.narshaback.projection.user.GetUserInGroup;

import java.util.List;

public interface UserGroupService {
    Boolean joinUser(JoinGroupDTO joinGroupDTO);
    List<GetUserInGroup> getUserListInGroup(String groupId);
}
