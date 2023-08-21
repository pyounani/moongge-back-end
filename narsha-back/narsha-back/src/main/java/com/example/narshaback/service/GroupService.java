package com.example.narshaback.service;
import com.example.narshaback.base.dto.group.CreateGroupDTO;
import com.example.narshaback.base.dto.group.UpdateTimeDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;

import java.util.Optional;


public interface GroupService {
    String createGroup(CreateGroupDTO createGroupDTO);
    String getUserGroupCode(String userId);
    String deleteGroup(String groupCode);

    UpdateTimeDTO updateTime(UpdateTimeDTO updateTimeDTO);

    UpdateTimeDTO getTime(String groupCode);
}
