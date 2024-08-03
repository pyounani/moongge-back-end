package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.user.UserDTO;
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
    void 유저_회원가입() {

        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO();

        UserDTO savedUserDTO = userService.register(userRegisterDTO);

        Optional<UserEntity> findUser = userRepository.findByUserId(savedUserDTO.getUserId());
        assertTrue(findUser.isPresent());

        UserEntity user = findUser.get();
        assertEquals(userRegisterDTO.getUserId(), user.getUserId());
        assertEquals(userRegisterDTO.getPassword(), user.getPassword());
        assertEquals(userRegisterDTO.getUserType(), user.getUserType());
        assertEquals(userRegisterDTO.getName(), user.getUserName());
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
        String groupCode = userService.getUserGroupCode(userId);

        // then
        assertEquals(savedGroup.get().getGroupCode(), groupCode);
    }

    private UserRegisterDTO buildUserRegisterDTO() {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .userId("userId")
                .password("password")
                .userType("teacher")
                .name("name")
                .build();
        return userRegisterDTO;
    }

    private CreateGroupDTO buildCreateGroupDTO(UserEntity user) {
        CreateGroupDTO createGroupDTO = new CreateGroupDTO();
        createGroupDTO.setGroupName("groupName");
        createGroupDTO.setSchool("school");
        createGroupDTO.setGrade(3);
        createGroupDTO.setGroupClass(5);
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
