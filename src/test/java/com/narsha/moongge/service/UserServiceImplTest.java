package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.user.*;
import com.narsha.moongge.base.exception.LoginIdNotFoundException;
import com.narsha.moongge.base.exception.LoginPasswordNotMatchException;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private AmazonS3Service amazonS3Service;

    private String uploadedFile;

    @AfterEach
    void deleteFileInS3() {
        if (uploadedFile != null) {
            amazonS3Service.deleteS3(uploadedFile);
        }
    }

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

    @Test
    void 유저_정보_업데이트() {

        // 회원가입 진행
        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO();
        userService.register(userRegisterDTO);

        MultipartFile multipartFile = createMultipartFile();
        UpdateUserProfileRequestDTO updateUserProfileRequestDTO = buildUpdateUserProfileDTO(userRegisterDTO);

        UserProfileDTO userProfileDTO = userService.updateProfile(userRegisterDTO.getUserId(), multipartFile, updateUserProfileRequestDTO);

        assertEquals(updateUserProfileRequestDTO.getBirth(), userProfileDTO.getBirth());
        assertEquals(updateUserProfileRequestDTO.getNickname(), userProfileDTO.getNickname());
        assertEquals(updateUserProfileRequestDTO.getIntro(), userProfileDTO.getIntro());

        uploadedFile = userProfileDTO.getProfileImage();
    }

    @Test
    void 유저_정보_조회하기() {

        // 회원가입 진행
        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO();
        userService.register(userRegisterDTO);

        MultipartFile multipartFile = createMultipartFile();
        UpdateUserProfileRequestDTO updateUserProfileRequestDTO = buildUpdateUserProfileDTO(userRegisterDTO);

        UserProfileDTO savedUserProfileDTO = userService.updateProfile(userRegisterDTO.getUserId(), multipartFile, updateUserProfileRequestDTO);

        // when
        UserProfileDTO findUserProfileDTO = userService.getProfile(userRegisterDTO.getUserId());

        assertEquals(savedUserProfileDTO.getUserId(), findUserProfileDTO.getUserId());
        assertEquals(savedUserProfileDTO.getUserType(), findUserProfileDTO.getUserType());
        assertEquals(savedUserProfileDTO.getUsername(), findUserProfileDTO.getUsername());
        assertEquals(savedUserProfileDTO.getNickname(), findUserProfileDTO.getNickname());
        assertEquals(savedUserProfileDTO.getProfileImage(), findUserProfileDTO.getProfileImage());
        assertEquals(savedUserProfileDTO.getBirth(), findUserProfileDTO.getBirth());
        assertEquals(savedUserProfileDTO.getIntro(), findUserProfileDTO.getIntro());

        uploadedFile = savedUserProfileDTO.getProfileImage();
    }

    @Test
    void 뱃지_리스트_업데이트() {
        // 회원가입 진행
        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO();
        userService.register(userRegisterDTO);

        Optional<UserEntity> findUser = userRepository.findByUserId(userRegisterDTO.getUserId());
        assertTrue(findUser.isPresent());
        UserEntity user = findUser.get();

        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        groupService.createGroup(createGroupDTO);

        // when
        String updateBadgeList = userService.updateBadgeList(user.getUserId(), 1);
        assertEquals(user.getBadgeList(), updateBadgeList);
    }

    @Test
    void 뱃지_리스트_조회하기() {
        // 회원가입 진행
        UserRegisterDTO userRegisterDTO = buildUserRegisterDTO();
        userService.register(userRegisterDTO);

        Optional<UserEntity> findUser = userRepository.findByUserId(userRegisterDTO.getUserId());
        assertTrue(findUser.isPresent());
        UserEntity user = findUser.get();

        CreateGroupDTO createGroupDTO = buildCreateGroupDTO(user);
        groupService.createGroup(createGroupDTO);
        String updateBadgeList = userService.updateBadgeList(user.getUserId(), 1);

        // when
        String findBadgeList = userService.getBadgeList(user.getUserId());

        assertEquals(updateBadgeList, findBadgeList);
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

    private UpdateUserProfileRequestDTO buildUpdateUserProfileDTO(UserRegisterDTO userRegisterDTO) {
        return UpdateUserProfileRequestDTO.builder()
                .userId(userRegisterDTO.getUserId())
                .birth("birth")
                .nickname("nickname")
                .intro("intro")
                .build();
    }

    private MultipartFile createMultipartFile() {
        return new MockMultipartFile("file", "testImage.jpg", "image/jpeg", "test image content".getBytes());
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

    private UserRegisterDTO buildUserRegisterDTO(String userId, String userType) {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .userId(userId)
                .password("password")
                .userType(userType)
                .name("name")
                .build();
        return userRegisterDTO;
    }
}
