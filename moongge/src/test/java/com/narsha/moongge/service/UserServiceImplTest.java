package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.user.UserDTO;
import com.narsha.moongge.base.dto.user.UserLoginDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
import com.narsha.moongge.base.exception.LoginIdNotFoundException;
import com.narsha.moongge.base.exception.LoginPasswordNotMatchException;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    void 중복된_유저_인지_확인하기() {

        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO();
        userService.register(userRegisterDTO);

        Boolean checkUserId = userService.checkUserId(userRegisterDTO.getUserId());
        assertFalse(checkUserId);
    }

    @Test
    void 중복된_유저_아닌_경우_확인하기() {

        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO();
        userService.register(userRegisterDTO);

        Boolean checkUserId = userService.checkUserId("newUserId");
        assertTrue(checkUserId);
    }

    @Test
    void 로그인() {
        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO();
        userService.register(userRegisterDTO);

        UserLoginDTO userLoginDTO = buildUserLoginDTO(userRegisterDTO);
        UserDTO userDTO = userService.login(userLoginDTO);

        assertEquals(userRegisterDTO.getUserId(), userDTO.getUserId());
        assertEquals(userRegisterDTO.getUserType(), userDTO.getUserType());
        assertEquals(userRegisterDTO.getName(), userDTO.getUsername());
    }

    @Test
    void 로그인_실패_잘못된_아이디() {

        // 회원가입을 안 한 상태
        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO();

        // 회원가입없이 로그인을 진행한 상태
        UserLoginDTO userLoginDTO = buildUserLoginDTO(userRegisterDTO);
        assertThrows(LoginIdNotFoundException.class, () -> userService.login(userLoginDTO));
    }

    @Test
    void 로그인_실패_잘못된_비밀번호() {

        // 회원가입 진행
        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO();
        userService.register(userRegisterDTO);

        // 잘못된 비밀번호로 로그인 시도
        UserLoginDTO userLoginDTO = buildUserLoginDTO(userRegisterDTO, "wrongPassword");
        assertThrows(LoginPasswordNotMatchException.class, () -> userService.login(userLoginDTO));
    }

    private UserLoginDTO buildUserLoginDTO(UserRegisterDTO userRegisterDTO) {
        return UserLoginDTO.builder()
                .userId(userRegisterDTO.getUserId())
                .password(userRegisterDTO.getPassword())
                .build();
    }

    private UserLoginDTO buildUserLoginDTO(UserRegisterDTO userRegisterDTO, String password) {
        return UserLoginDTO.builder()
                .userId(userRegisterDTO.getUserId())
                .password(password)
                .build();
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
}
