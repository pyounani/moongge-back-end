package com.narsha.moongge.service;


import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.user.*;
import com.narsha.moongge.base.exception.*;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 생성자 작성 생략
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    /**
     * 회원가입
     */
    @Override
    public UserDTO register(UserRegisterDTO userRegisterDTO) {

        // 중복된 유저 있을 때
        Optional<UserEntity> existingUser = userRepository.findByUserId(userRegisterDTO.getUserId());
        if (existingUser.isPresent()) {
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
        Optional<UserEntity> user = userRepository.findByUserId(userId);

        if (user.isPresent()) return false;
        return true; // 사용 가능하면 true
    }

    /**
     * 로그인
     */
    @Override
    public UserDTO login(UserLoginDTO userLoginDTO) {
        UserEntity findUser = userRepository.findByUserId(userLoginDTO.userId)
                .orElseThrow(() -> new LoginIdNotFoundException(ErrorCode.USERID_NOT_FOUND));

        // 비밀번호가 같은지 확인
        if(!findUser.getPassword().equals(userLoginDTO.password))
            throw new LoginPasswordNotMatchException(ErrorCode.PASSWORD_NOT_MATCH);

        return UserDTO.mapToUserDTO(findUser);
    }

    //

    //프로필 수정(프로필 최초 설정)
    @Override
    public UserEntity updateProfile(UpdateUserProfileDTO updateUserProfileDTO) {

        // 특정 프로필 객체 업데이트
        Optional<UserEntity> profile = userRepository.findByUserId(updateUserProfileDTO.getUserId());

        if(!profile.isPresent())
            throw new ProfileNotFoundException(ErrorCode.PROFILE_NOT_FOUND);

        if(updateUserProfileDTO.getProfileImage() != null)
            profile.get().setProfileImage(updateUserProfileDTO.getProfileImage());
        profile.get().setBirth(updateUserProfileDTO.getBirth());
        profile.get().setIntro(updateUserProfileDTO.getIntro());
        profile.get().setNickname(updateUserProfileDTO.getNikname());

        return userRepository.save(profile.get());
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

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        return user.getBadgeList();
    }

    /**
     * 뱃지 리스트 업데이트
     */
    @Override
    public String updateBadgeList(String userId, Integer achieveNum) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        List<Boolean> badgeList = parseBadgeList(user.getBadgeList());
        updateBadgeListInUser(badgeList, achieveNum);

        // 뱃지 리스트 업데이트
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

    /**
     * 지정된 인덱스의 배지 상태를 true로 업데이트합니다.
     */
    private void updateBadgeListInUser(List<Boolean> badgeList, Integer achieveNum) {
        if (achieveNum <= 0 || achieveNum > badgeList.size()) {
            throw new IllegalArgumentException("유효하지 않은 achieveNum: " + achieveNum);
        }

        badgeList.set(achieveNum - 1, true);
    }
}
