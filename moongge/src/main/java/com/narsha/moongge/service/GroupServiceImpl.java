package com.narsha.moongge.service;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.base.exception.DeleteFailedEntityRelatedGroupCodeException;
import com.narsha.moongge.base.exception.GroupCodeNotFoundException;
import com.narsha.moongge.base.exception.GroupNotFoundException;
import com.narsha.moongge.base.exception.LoginIdNotFoundException;
import com.narsha.moongge.entity.*;
import com.narsha.moongge.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final NoticeRepository noticeRepository;
    private final AlarmRepository alarmRepository;

    /**
     * 그룹 생성하기
     */
    @Override
    @Transactional
    public String createGroup(CreateGroupDTO createGroupDTO) {

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
        UserEntity user = userRepository.findByUserId(createGroupDTO.getUserId())
                .orElseThrow(() -> new LoginIdNotFoundException(ErrorCode.USERID_NOT_FOUND));
        user.updateGroup(createdGroup);

        // profile badge update
        initialBadgeList(user);

        return user.getUserId();
    }

    /**
     * 그룹 삭제하기
     */
    @Override
    @Transactional
    public String deleteGroup(String groupCode) {

        GroupEntity group = groupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND));

        // 유저의 그룹 제거(null 값으로)
        clearGroupForUsers(group);

        groupRepository.delete(group);

        return groupCode;
    }

    @Override
    public UpdateTimeDTO updateTime(UpdateTimeDTO updateTimeDTO) {
        Optional<GroupEntity> findGroup = groupRepository.findByGroupCode(updateTimeDTO.getGroupCode());

        if(findGroup.isPresent()){
            try{
                // setting property
                findGroup.get().setStartTime(updateTimeDTO.getStartTime());
                findGroup.get().setEndTime(updateTimeDTO.getEndTime());

                // save
                groupRepository.save(findGroup.get());

                // response setting
                UpdateTimeDTO update = new UpdateTimeDTO();
                update.setGroupCode(updateTimeDTO.getGroupCode());
                update.setStartTime(findGroup.get().getStartTime());
                update.setEndTime(findGroup.get().getEndTime());

                return update;

            } catch(Exception e){
                throw new DeleteFailedEntityRelatedGroupCodeException(ErrorCode.DELETE_FAILED_ENTITY_RELATED_GROUPCODE);
            }
        } else{
            throw new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);
        }

    }

    @Override
    public UpdateTimeDTO getTime(String groupCode) {

        Optional<GroupEntity> findGroup = groupRepository.findByGroupCode(groupCode);

        if(findGroup.isPresent()){
            try{
                // response setting
                UpdateTimeDTO update = new UpdateTimeDTO();
                update.setGroupCode(findGroup.get().getGroupCode());
                update.setStartTime(findGroup.get().getStartTime());
                update.setEndTime(findGroup.get().getEndTime());

                return update;

            } catch(Exception e){
                throw new DeleteFailedEntityRelatedGroupCodeException(ErrorCode.DELETE_FAILED_ENTITY_RELATED_GROUPCODE);
            }
        } else{
            throw new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);
        }

    }

    // 랜덤 코드 생성
    private String getRandomCode(int length) {
        String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
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
            groupCode = getRandomCode(10);
        } while (groupRepository.findByGroupCode(groupCode).isPresent()); // 동일한 그룹 코드가 나오지 않도록
        return groupCode;
    }

    private GroupEntity buildGroupEntity(CreateGroupDTO createGroupDTO, String groupCode) {
        GroupEntity group = GroupEntity.builder()
                .groupName(createGroupDTO.getGroupName())
                .school(createGroupDTO.getSchool())
                .grade(createGroupDTO.getGrade())
                .groupCode(groupCode)
                .groupClass(createGroupDTO.getGroup_class())
                .build();
        return group;
    }

    private void initialBadgeList(UserEntity user) {
        List<Boolean> newBadgeList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
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

