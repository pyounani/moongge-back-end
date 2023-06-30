package com.example.narshaback.service;

import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.projection.user.GetUserInGroup;
import com.example.narshaback.base.projection.user_group.GetJoinGroupList;

import java.util.List;

public interface UserGroupService {
    Boolean joinUser(JoinGroupDTO joinGroupDTO);
    List<GetUserInGroup> getUserListInGroup(String groupId);
    String getUserGroupCode(Integer id);
    List<GetJoinGroupList> getJoinGroupList(String userId);
}
