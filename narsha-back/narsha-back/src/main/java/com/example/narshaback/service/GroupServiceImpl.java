package com.example.narshaback.service;

import com.example.narshaback.dto.CreateGroupDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.UserGroupRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    @Override
    public Integer createGroup(CreateGroupDTO createGroupDTO) {
        // 그룹 생성
        GroupEntity group = GroupEntity.builder()
                .groupName(createGroupDTO.getGroupName())
                .school(createGroupDTO.getSchool())
                .grade(createGroupDTO.getGrade())
                .group_class(createGroupDTO.getGroup_class())
                .build();
        // DB에 그룹 생성, 코드 return
        GroupEntity createGroup = groupRepository.save(group);
        if (createGroupDTO.getUserId() == null || createGroup == null){
            return null;
        }
            // user, group 연결
            User_Group userToGroup = User_Group.builder()
                    .user(userRepository.findByUserId(createGroupDTO.getUserId()))
                    .group(createGroup)
                    .build();
        return userGroupRepository.save(userToGroup).getId();
    }
}
