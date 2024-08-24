package com.narsha.moongge.service;


import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.user.*;
import com.narsha.moongge.base.exception.*;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // 생성자 작성 생략
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AmazonS3Service amazonS3Service;

    /**
     * 회원가입
     */
    @Override
    public UserDTO register(UserRegisterDTO userRegisterDTO) {

        // 중복된 유저 있을 때
        if (userRepository.existsByUserId(userRegisterDTO.getUserId())) {
            throw new RegisterException(ErrorCode.DUPLICATE_ID_REQUEST);
        }

        UserEntity user = UserEntity.builder()
                .userId(userRegisterDTO.getUserId())
                .userType(userRegisterDTO.getUserType())
                .password(userRegisterDTO.getPassword())
                .userName(userRegisterDTO.getName())
                .build();

        UserEntity savedUser = userRepository.save(user);

        return UserDTO.mapToUserDTO(savedUser);
    }

    /**
     * 아이디 중복 확인하기
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean checkUserId(String userId) {
        return !userRepository.existsByUserId(userId);
    }

    /**
     * 로그인
     */
    @Override
    public UserDTO login(UserLoginDTO userLoginDTO) {
        UserEntity findUser = userRepository.findByUserId(userLoginDTO.userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호가 같은지 확인
        if(!findUser.getPassword().equals(userLoginDTO.password))
            throw new LoginPasswordNotMatchException(ErrorCode.PASSWORD_NOT_MATCH);

        return UserDTO.mapToUserDTO(findUser);
    }

    /**
     * 유저 정보 업데이트
     */
    @Override
    public UserProfileDTO updateProfile(String userId, MultipartFile multipartFile, UpdateUserRequestDTO updateUserRequestDTO) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 기존 프로필 이미지가 있을 경우 삭제
        String existingProfileImage = user.getProfileImage();
        if (existingProfileImage != null && !existingProfileImage.isEmpty()) {
            amazonS3Service.deleteS3(existingProfileImage);
        }

        // 새로운 프로필 이미지 S3에 업로드
        String imageUrl = amazonS3Service.uploadFileToS3(multipartFile, "users/profileImages");

        // 유저 정보 업데이트
        user.updateProfile(updateUserRequestDTO.getBirth(),
                updateUserRequestDTO.getNickname(),
                updateUserRequestDTO.getIntro(),
                imageUrl);

        return UserProfileDTO.mapToUserProfileDTO(user);
    }

    /**
     * 유저 정보 가져오기
     */
    @Override
    @Transactional(readOnly = true)
    public UserProfileDTO getProfile(String userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        return UserProfileDTO.mapToUserProfileDTO(user);
    }

    /**
     * 뱃지 리스트 가져오기
     */
    @Override
    @Transactional(readOnly = true)
    public String getBadgeList(String userId) {
        return userRepository.findBadgeListByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 뱃지 리스트 업데이트
     */
    @Override
    public String updateBadgeList(String userId, Integer achieveNum) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        List<Boolean> badgeList = parseBadgeList(user.getBadgeList());

        // 업적이 이미 달성되었는지 확인
        if (badgeList.get(achieveNum - 1)) {
            throw new AchievementAlreadyCompletedException(ErrorCode.ACHIEVEMENT_ALREADY_COMPLETED);
        }

        // 뱃지 리스트 업데이트
        badgeList.set(achieveNum - 1, true);
        user.updateBadgeList(new JSONArray(badgeList).toString());

        return user.getBadgeList();
    }

    /**
     * JSON 문자열을 List<Boolean>로 변환합니다.
     */
    private List<Boolean> parseBadgeList(String badgeListStr) {
        try {
            JSONArray jsonArray = new JSONArray(badgeListStr);
            List<Boolean> badgeList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                badgeList.add(jsonArray.getBoolean(i));
            }

            return badgeList;
        } catch (JSONException e) {
            throw new RuntimeException("배지 리스트 JSON 파싱 실패", e);
        }
    }

}
