package com.example.narshaback.service;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.dto.group.CreateGroupDTO;
import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.exception.GroupNotFoundException;
import com.example.narshaback.base.exception.LoginIdNotFoundException;
import com.example.narshaback.base.exception.LoginPasswordNotMatchException;
import com.example.narshaback.base.exception.RegisterException;
import com.example.narshaback.base.projection.user.GetUserInGroup;
import com.example.narshaback.base.projection.user_group.GetJoinGroupList;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public UserEntity createGroup(CreateGroupDTO createGroupDTO) {
        // 그룹 코드 생성
        String groupCode;
        do{
            groupCode = getRandomCode(10);
        }while(groupRepository.findByGroupCode(groupCode).isPresent());
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

        // profile badge update
        List<Boolean> newBadgeList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            newBadgeList.add(false);
        }

        Optional<UserEntity> user = userRepository.findByUserId(createGroupDTO.getUserId());
        if (!user.isPresent()){
            throw new LoginIdNotFoundException(ErrorCode.USERID_NOT_FOUND);
        } else {
            user.get().setBadgeList(newBadgeList.toString());
            user.get().setGroupCode(group);

            return userRepository.save(user.get());
        }
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

    @Override
    public String getUserGroupCode(String userId) {

        Optional<UserEntity> user = userRepository.findByUserId(userId);

        if(!user.isPresent()){
            throw new LoginIdNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        try{
            String group = userRepository.findByUserId(userId).get().getGroupCode().getGroupCode();
            Optional<GroupEntity> groupCode = groupRepository.findByGroupCode(group);
            return groupCode.get().getGroupCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}

