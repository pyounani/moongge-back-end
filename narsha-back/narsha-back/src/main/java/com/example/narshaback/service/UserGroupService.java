package com.example.narshaback.service;

import com.example.narshaback.dto.group.JoinGroupDTO;
import com.example.narshaback.projection.user.GetUserInGroup;
import com.example.narshaback.projection.user_group.GetJoinGroupList;

import java.util.List;

public interface UserGroupService {
    Boolean joinUser(JoinGroupDTO joinGroupDTO);
    List<GetUserInGroup> getUserListInGroup(String groupId);
    List<GetJoinGroupList> getJoinGroupList(String userId);
}
