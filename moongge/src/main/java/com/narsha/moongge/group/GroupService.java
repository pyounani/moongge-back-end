package com.narsha.moongge.group;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.group.dto.CreateGroupDTO;
import com.narsha.moongge.group.dto.UpdateTimeDTO;
import com.narsha.moongge.base.exception.DeleteFailedEntityRelatedGroupCodeException;
import com.narsha.moongge.base.exception.GroupCodeNotFoundException;
import com.narsha.moongge.base.exception.GroupNotFoundException;
import com.narsha.moongge.base.exception.LoginIdNotFoundException;
import com.narsha.moongge.entity.*;
import com.narsha.moongge.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupService {

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
    public String deleteGroup(String groupCode) {

        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupCode);

        if(group.isPresent()){
            try{
                // delete
                Optional<AlarmEntity> delAlarm = alarmRepository.deleteByGroupCode(group.get());
                Optional<LikeEntity> delLike =  likeRepository.deleteByGroupCode(group.get());
                Optional<CommentEntity> delComment =  commentRepository.deleteByGroupCode(group.get());
                Optional<NoticeEntity> delNotice =  noticeRepository.deleteByGroupCode(group.get());
                Optional<PostEntity> delPost =  postRepository.deleteByGroupCode(group.get());
                Optional<UserEntity> delUser =  userRepository.deleteByGroupCode(group.get());
                Optional<GroupEntity> delGroup = groupRepository.deleteByGroupCode(groupCode);
            } catch(Exception e){
                throw new DeleteFailedEntityRelatedGroupCodeException(ErrorCode.DELETE_FAILED_ENTITY_RELATED_GROUPCODE);
            }
        } else{
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
        }

        return group.get().getGroupCode();
    }

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

}

