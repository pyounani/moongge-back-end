package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.group.UpdateTimeDTO;

public interface GroupService {

    String createGroup(CreateGroupDTO createGroupDTO);
    String deleteGroup(String groupCode);

    UpdateTimeDTO updateTime(String groupCode, UpdateTimeDTO updateTimeDTO);

    UpdateTimeDTO getTime(String groupCode);
}
