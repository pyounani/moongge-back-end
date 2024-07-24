package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.UpdateTimeDTO;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
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
    @Autowired
    private UserService userService;

    @Test
    void 그룹_생성_테스트() {

        // given
        UserEntity user = createUser();

        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);

        // when
        String userId = groupService.createGroup(createGroupDTO);

        // then
        assertEquals(user.getUserId(), userId);

        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(user.getGroup().getGroupCode());
        assertTrue(savedGroup.isPresent());

        GroupEntity group = savedGroup.get();
        assertEquals("groupName", group.getGroupName());
        assertEquals("school", group.getSchool());
        assertEquals(3, group.getGrade());
        assertEquals(5, group.getGroupClass());
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
        // 유저를 다시 조회하여 그룹 필드가 null로 설정되었는지 확인
        Optional<UserEntity> updatedUserOpt = userRepository.findByUserId(user.getUserId());
        assertTrue(updatedUserOpt.isPresent(), "유저는 여전히 존재해야 합니다.");
        UserEntity updatedUser = updatedUserOpt.get();
        assertNull(updatedUser.getGroup(), "그룹 삭제 후 유저의 그룹은 null이어야 합니다.");

        // 그룹이 실제로 삭제되었는지 확인
        Optional<GroupEntity> deletedGroupOpt = groupRepository.findByGroupCode(groupCode);
        assertFalse(deletedGroupOpt.isPresent(), "그룹은 삭제되어야 합니다.");
    }

    @Test
    void 그룹_시간_등록() {
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

        // when
        groupService.updateTime(groupCode, updateTimeDTO);

        // then
        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(user.getGroup().getGroupCode());
        assertTrue(savedGroup.isPresent());

        GroupEntity group = savedGroup.get();
        assertEquals(startTime, group.getStartTime());
        assertEquals(endTime, group.getEndTime());
    }

    private CreateGroupDTO buildCreateGroupDTO(UserEntity user) {
        CreateGroupDTO createGroupDTO = new CreateGroupDTO();
        createGroupDTO.setGroupName("groupName");
        createGroupDTO.setSchool("school");
        createGroupDTO.setGrade(3);
        createGroupDTO.setGroup_class(5);
        createGroupDTO.setUserId(user.getUserId());
        return createGroupDTO;
    }

    private UserEntity createUser() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUserId("userId");
        userRegisterDTO.setUserType("teacher");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setName("name");
        userService.register(userRegisterDTO);

        Optional<UserEntity> savedUser = userRepository.findByUserId("userId");
        assertTrue(savedUser.isPresent());
        UserEntity user = savedUser.get();
        return user;
    }
}