package com.narsha.moongge.group;

import com.narsha.moongge.group.dto.CreateGroupDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.UserRepository;
import com.narsha.moongge.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupServiceTest {

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

        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(user.getGroupCode().getGroupCode());
        assertTrue(savedGroup.isPresent());

        GroupEntity group = savedGroup.get();
        assertEquals("groupName", group.getGroupName());
        assertEquals("school", group.getSchool());
        assertEquals(3, group.getGrade());
        assertEquals(5, group.getGroupClass());
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