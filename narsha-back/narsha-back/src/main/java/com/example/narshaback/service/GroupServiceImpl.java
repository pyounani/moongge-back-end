package com.example.narshaback.service;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.dto.group.CreateGroupDTO;
import com.example.narshaback.base.dto.group.UpdateTimeDTO;
import com.example.narshaback.base.exception.DeleteFailedEntityRelatedGroupCodeException;
import com.example.narshaback.base.exception.GroupCodeNotFoundException;
import com.example.narshaback.base.exception.GroupNotFoundException;
import com.example.narshaback.base.exception.LoginIdNotFoundException;
import com.example.narshaback.entity.*;
import com.example.narshaback.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public String createGroup(CreateGroupDTO createGroupDTO) {
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

            return userRepository.save(user.get()).getUserId();
        }
    }

    // 랜덤 코드 생성
    public String getRandomCode(int length) {
        String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
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

    @Transactional
    @Override
    public String deleteGroup(String groupCode) {

        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupCode);

        if(group.isPresent()){
            try{
                // delete
                Optional<GroupEntity> delGroup = groupRepository.deleteByGroupCode(groupCode);
                Optional<LikeEntity> delLike =  likeRepository.deleteByGroupCode(group.get());
                Optional<CommentEntity> delComment =  commentRepository.deleteByGroupCode(group.get());
                Optional<NoticeEntity> delNotice =  noticeRepository.deleteByGroupCode(group.get());
                Optional<PostEntity> delPost =  postRepository.deleteByGroupCode(group.get());
                Optional<UserEntity> delUser =  userRepository.deleteByGroupCode(group.get());
            } catch(Exception e){
                throw new DeleteFailedEntityRelatedGroupCodeException(ErrorCode.DELETE_FAILED_ENTITY_RELATED_GROUPCODE);
            }
        } else{
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
        }

        return group.get().getGroupCode();
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
}

