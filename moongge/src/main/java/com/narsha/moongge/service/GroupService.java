package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.group.GroupDTO;
import com.narsha.moongge.base.dto.group.JoinGroupDTO;
import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.base.dto.user.UserProfileDTO;

import java.util.List;

public interface GroupService {

    String createGroup(CreateGroupDTO createGroupDTO);
    String getUserGroupCode(String userId);
    GroupDTO joinGroup(JoinGroupDTO joinGroupDTO);
    String deleteGroup(String groupCode);
    UpdateTimeDTO updateTime(String groupCode, UpdateTimeDTO updateTimeDTO);
    UpdateTimeDTO getTime(String groupCode);
    List<UserProfileDTO> getStudentList(String groupCode, String userId);
}
