package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupServiceImplTest {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    void 그룹_생성하기() {
        // given
        UserEntity user = createUser();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);

        // when
        String userId = groupService.createGroup(createGroupDTO);

        // then
        assertEquals(user.getUserId(), userId, "생성된 그룹의 유저 ID가 일치해야 합니다.");

        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(user.getGroup().getGroupCode());
        assertTrue(savedGroup.isPresent(), "그룹이 저장되어 있어야 합니다.");

        GroupEntity group = savedGroup.get();
        assertEquals(createGroupDTO.getGroupName(), group.getGroupName(), "그룹 이름이 일치해야 합니다.");
        assertEquals(createGroupDTO.getSchool(), group.getSchool(), "학교 이름이 일치해야 합니다.");
        assertEquals(createGroupDTO.getGrade(), group.getGrade(), "학년이 일치해야 합니다.");
        assertEquals(createGroupDTO.getGroupClass(), group.getGroupClass(), "반이 일치해야 합니다.");
    }

    @Test
    void 그룹_코드_불러오기() {

        // given
        UserEntity user = createUser();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        String userId = groupService.createGroup(createGroupDTO);

        assertEquals(user.getUserId(), userId);

        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(user.getGroup().getGroupCode());
        assertTrue(savedGroup.isPresent());

        // when
        String groupCode = groupService.getUserGroupCode(userId);

        // then
        assertEquals(savedGroup.get().getGroupCode(), groupCode);
    }

    @Test
    void 그룹_삭제하기() {
        // given
        UserEntity user = createUser();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        groupService.createGroup(createGroupDTO);

        String groupCode = user.getGroup().getGroupCode();

        // when
        groupService.deleteGroup(groupCode);

        // then
        Optional<UserEntity> updatedUserOpt = userRepository.findByUserId(user.getUserId());
        assertTrue(updatedUserOpt.isPresent(), "유저는 여전히 존재해야 합니다.");
        UserEntity updatedUser = updatedUserOpt.get();
        assertNull(updatedUser.getGroup(), "그룹 삭제 후 유저의 그룹은 null이어야 합니다.");

        Optional<GroupEntity> deletedGroupOpt = groupRepository.findByGroupCode(groupCode);
        assertFalse(deletedGroupOpt.isPresent(), "그룹은 삭제되어야 합니다.");
    }

    @Test
    void 그룹_시간_등록하기() {
        // given
        UserEntity user = createUser();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        groupService.createGroup(createGroupDTO);

        String groupCode = user.getGroup().getGroupCode();

        UpdateTimeDTO updateTimeDTO = UpdateTimeDTO.builder()
                .startTime(LocalTime.of(13, 0, 0))
                .endTime(LocalTime.of(15, 0, 0))
                .build();

        // when
        groupService.updateTime(groupCode, updateTimeDTO);

        // then
        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(groupCode);
        assertTrue(savedGroup.isPresent(), "그룹이 존재해야 합니다.");

        GroupEntity group = savedGroup.get();
        assertEquals(LocalTime.of(13, 0, 0), group.getStartTime(), "시작 시간이 일치해야 합니다.");
        assertEquals(LocalTime.of(15, 0, 0), group.getEndTime(), "종료 시간이 일치해야 합니다.");
    }

    @Test
    void 그룹_시간_조회하기() {
        // given
        UserEntity user = createUser();
        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        groupService.createGroup(createGroupDTO);

        String groupCode = user.getGroup().getGroupCode();

        UpdateTimeDTO updateTimeDTO = new UpdateTimeDTO();
        LocalTime startTime = LocalTime.of(13, 0, 0); // 13:00:00
        LocalTime endTime = LocalTime.of(15, 0, 0);   // 15:00:00

        updateTimeDTO.setStartTime(startTime);
        updateTimeDTO.setEndTime(endTime);

        groupService.updateTime(groupCode, updateTimeDTO);

        // when
        UpdateTimeDTO retrievedTimeDTO = groupService.getTime(groupCode);

        // then
        assertNotNull(retrievedTimeDTO, "그룹 시간 정보가 존재해야 합니다.");
        assertEquals(startTime, retrievedTimeDTO.getStartTime(), "시작 시간이 일치해야 합니다.");
        assertEquals(endTime, retrievedTimeDTO.getEndTime(), "종료 시간이 일치해야 합니다.");
        assertEquals(groupCode, retrievedTimeDTO.getGroupCode(), "그룹 코드가 일치해야 합니다.");
    }

    private CreateGroupDTO buildCreateGroupDTO(UserEntity user) {
        return CreateGroupDTO.builder()
                .groupName("groupName")
                .school("school")
                .grade(3)
                .groupClass(5)
                .userId(user.getUserId())
                .build();
    }

    private UserEntity createUser() {
        UserEntity user = UserEntity.builder()
                .userId("userId")
                .userType("teacher")
                .password("password")
                .userName("name")
                .build();

        return userRepository.save(user);
    }
}
