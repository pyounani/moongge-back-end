package com.example.narshaback.service;

import com.example.narshaback.dto.group.CreateGroupDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.ProfileEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.ProfileRepository;
import com.example.narshaback.repository.UserGroupRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    @Override
    public Integer createGroup(CreateGroupDTO createGroupDTO) {
        // 그룹 코드 생성
        String groupCode;
        do{
            groupCode = getRandomCode(10);
        }while(groupRepository.findByGroupCode(groupCode) != null);
        // 동일한 그룹 코드가 나오지 않도록

        // 그룹 생성
        GroupEntity group = GroupEntity.builder()
                .groupName(createGroupDTO.getGroupName())
                .school(createGroupDTO.getSchool())
                .grade(createGroupDTO.getGrade())
                .groupCode(groupCode)
                .groupClass(createGroupDTO.getGroup_class())
                .build();
        // DB에 그룹 생성, 코드 return
        GroupEntity createGroup = groupRepository.save(group);
        if (createGroupDTO.getUserId() == null || createGroup.getGroupCode() == null){
            return null;
        }
            // user, group 연결
            User_Group userToGroup = User_Group.builder()
                    .userId(userRepository.findByUserId(createGroupDTO.getUserId()))
                    .groupCode(createGroup)
                    .build();
        User_Group createUserGroup = userGroupRepository.save(userToGroup);

            // profile 생성
        List<Boolean> newBadgeList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            newBadgeList.add(false);
        }

        ProfileEntity profile = ProfileEntity.builder()
            .userGroupId(userToGroup)
                .badgeList(newBadgeList.toString())
            .build();
        profileRepository.save(profile);

        return createUserGroup.getUserGroupId();
    }

    // 랜덤 코드 생성
    public String getRandomCode(int length) {
        String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$^*";
        int alphaNumLength = alphaNum.length();

        Random random = new Random();

        StringBuffer code = new StringBuffer();
        for (int i = 0; i < length; i++) {
            code.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
        }

        return code.toString();
    }
}

