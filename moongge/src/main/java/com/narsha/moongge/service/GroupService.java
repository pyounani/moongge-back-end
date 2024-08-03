package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.group.GroupDTO;
import com.narsha.moongge.base.dto.group.JoinGroupDTO;
import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.entity.UserEntity;

public interface GroupService {

    String createGroup(CreateGroupDTO createGroupDTO);
    String getUserGroupCode(String userId);
    GroupDTO joinUser(JoinGroupDTO joinGroupDTO);
    String deleteGroup(String groupCode);
    UpdateTimeDTO updateTime(String groupCode, UpdateTimeDTO updateTimeDTO);
    UpdateTimeDTO getTime(String groupCode);

}
