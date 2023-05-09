package com.example.narshaback.service;

import com.example.narshaback.dto.JoinGroupDTO;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl implements UserGroupService{

    private final UserGroupRepository userGroupRepository;

    @Override
    public Boolean joinUser(JoinGroupDTO joinGroupDTO) {
        // 해당 그룹이 없을 때 return null
        User_Group user_group = User_Group.builder()
                .user(joinGroupDTO.getUser())
                .group(joinGroupDTO.getGroup())
                .build();
        userGroupRepository.save(user_group);
        return true;
    }
}
