package com.example.narshaback.service;

import com.example.narshaback.dto.CreateGroupDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.UserGroupRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    @Override
    public Integer createGroup(CreateGroupDTO createGroupDTO) {
        // 그룹 코드 생성
        String groupCode;
        do{
            groupCode = getRandomCode(20);
        }while(groupRepository.findByGroupCode(groupCode) != null);
        // 동일한 그룹 코드가 나오지 않도록

        // 그룹 생성
        GroupEntity group = GroupEntity.builder()
                .groupName(createGroupDTO.getGroupName())
                .school(createGroupDTO.getSchool())
                .grade(createGroupDTO.getGrade())
                .groupCode(groupCode)
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

    // 랜덤 코드 생성
    public String getRandomCode(int length) {
        String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        int alphaNumLength = alphaNum.length();

        Random random = new Random();

        StringBuffer code = new StringBuffer();
        for (int i = 0; i < length; i++) {
            code.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
        }

        return code.toString();
    }
}

