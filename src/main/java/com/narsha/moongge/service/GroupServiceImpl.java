package com.narsha.moongge.service;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.group.GroupDTO;
import com.narsha.moongge.base.dto.group.JoinGroupDTO;
import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.base.dto.user.UserProfileDTO;
import com.narsha.moongge.base.exception.*;
import com.narsha.moongge.entity.*;
import com.narsha.moongge.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    public static final int BADGE_LIST_SIZE = 10;
    private static final int GROUP_CODE_SIZE = 10;

    @Value("${group.code}")
    private String alphaNum;

    /**
     * 그룹 생성하기
     */
    @Override
    public String createGroup(CreateGroupDTO createGroupDTO) {

        UserEntity user = userRepository.findByUserId(createGroupDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 학생 유형 사용자 검증
        if ("student".equals(user.getUserType())) {
            throw new StudentGroupCreationException(ErrorCode.STUDENT_NOT_ALLOWED_GROUP);
        }

        // 그룹 코드 생성
        String groupCode = generateUniqueGroupCode();
        // 그룹 생성
        GroupEntity group = buildGroupEntity(createGroupDTO, groupCode);
        // DB에 그룹 생성
        GroupEntity createdGroup  = groupRepository.save(group);
        if (createdGroup  == null || createdGroup .getGroupCode() == null) {
            return null;
        }

        // 사용자에 생성된 그룹 업데이트
        user.updateGroup(createdGroup);
        initialBadgeList(user);

        return user.getUserId();
    }

    /**
     * 그룹 코드 가져오기
     */
    @Override
    @Transactional(readOnly=true)
    public String getUserGroupCode(String userId) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        GroupEntity group = Optional.ofNullable(user.getGroup())
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        String groupCode = Optional.ofNullable(group.getGroupCode())
                .orElseThrow(() -> new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND));

        groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        return groupCode;
    }

    /**
     * 그룹에 가입하기
     */
    @Override
    public GroupDTO joinGroup(JoinGroupDTO joinGroupDTO) {
        GroupEntity group = groupRepository.findByGroupCode(joinGroupDTO.getGroupCode())
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        // set group code
        UserEntity user = userRepository.findByUserId(joinGroupDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 그룹에 조인
        user.updateGroup(group);

        // badgeList 생성
        initialBadgeList(user);

        return GroupDTO.mapToGroupDTO(user);
    }

    /**
     * 그룹 삭제하기
     */
    @Override
    public String deleteGroup(String groupCode) {

        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        // 유저의 그룹 제거(null 값으로)
        clearGroupForUsers(group);

        groupRepository.delete(group);

        return groupCode;
    }

    /**
     * 그룹 시간 등록(수정)하기
     */
    @Override
    public UpdateTimeDTO updateTime(String groupCode, UpdateTimeDTO updateTimeDTO) {

        GroupEntity findGroup = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        findGroup.setTime(updateTimeDTO.getStartTime(), updateTimeDTO.getEndTime());

        return UpdateTimeDTO.builder()
                .groupCode(groupCode)
                .startTime(findGroup.getStartTime())
                .endTime(findGroup.getEndTime())
                .build();
    }

    /**
     * 그룹 시간 불러오기
     */
    @Override
    @Transactional(readOnly = true)
    public UpdateTimeDTO getTime(String groupCode) {

        GroupEntity findGroup = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        return UpdateTimeDTO.builder()
                .groupCode(groupCode)
                .startTime(findGroup.getStartTime())
                .endTime(findGroup.getEndTime())
                .build();
    }

    /**
     * 그룹의 유저 목록 가져오기(요청한 유저 제외)
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserProfileDTO> getStudentList(String groupCode, String userId) {
        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND));

        userRepository.findByUserIdAndGroup(userId, group)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        List<UserEntity> studentList = userRepository.findByGroupAndUserIdNotLike(group, userId);

        return studentList.stream()
                .map(UserProfileDTO::mapToUserProfileDTO)
                .collect(Collectors.toList());
    }

    // 랜덤 코드 생성
    private String getRandomCode(int length) {
        int alphaNumLength = alphaNum.length();

        Random random = new Random();

        StringBuffer code = new StringBuffer();
        for (int i = 0; i < length; i++) {
            code.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
        }

        return code.toString();
    }

    private String generateUniqueGroupCode() {
        String groupCode;
        do {
            groupCode = getRandomCode(GROUP_CODE_SIZE);
        } while (groupRepository.existsByGroupCode(groupCode)); // 동일한 그룹 코드가 나오지 않도록
        return groupCode;
    }

    private GroupEntity buildGroupEntity(CreateGroupDTO createGroupDTO, String groupCode) {
        GroupEntity group = GroupEntity.builder()
                .groupName(createGroupDTO.getGroupName())
                .school(createGroupDTO.getSchool())
                .grade(createGroupDTO.getGrade())
                .groupCode(groupCode)
                .groupClass(createGroupDTO.getGroupClass())
                .build();
        return group;
    }

    private void initialBadgeList(UserEntity user) {
        List<Boolean> newBadgeList = new ArrayList<>(BADGE_LIST_SIZE);
        for (int i = 0; i < BADGE_LIST_SIZE; i++) {
            newBadgeList.add(false);
        }
        user.updateBadgeList(newBadgeList.toString());
    }

    private void clearGroupForUsers(GroupEntity group) {
        List<UserEntity> users = userRepository.findByGroup(group);
        for (UserEntity user : users) {
            user.clearGroup();
        }
    }

}

