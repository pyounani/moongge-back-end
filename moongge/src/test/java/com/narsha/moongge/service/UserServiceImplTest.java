package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

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
        String groupCode = userService.getUserGroupCode(userId);

        // then
        assertEquals(savedGroup.get().getGroupCode(), groupCode);
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
