package com.narsha.moongge.service;


import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.user.*;
import com.narsha.moongge.base.exception.*;
import com.narsha.moongge.base.projection.user.GetUser;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //뱃지 추가
    @Override
    public String updateBadgeList(String userId, Integer achNum) {
        Optional<UserEntity> profile = userRepository.findByUserId(userId);

        if(!profile.isPresent())
            throw new ProfileNotFoundException(ErrorCode.PROFILE_NOT_FOUND);

        UserEntity userProfile = profile.get();

        // parse object
        String badgeListStr = userProfile.getBadgeList();
        JSONParser parser = new JSONParser(badgeListStr);

        try {
            // string parse & convert array
            List<Boolean> badgeList = (List<Boolean>) parser.parse();
            badgeList.set(achNum - 1, true);

            // update
            userProfile.setBadgeList(badgeList.toString());
            userRepository.save(userProfile);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return userProfile.getBadgeList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetUser> getStudentList(String GroupId, String userId) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(GroupId);
        if(!group.isPresent())
            throw new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);

        Optional<UserEntity> profile = userRepository.findByUserId(userId);
        if(!profile.isPresent())
            throw new ProfileNotFoundException(ErrorCode.PROFILE_NOT_FOUND);

//        List<GetUser> studentList = userRepository.findByGroupCode(group.get());
        List<GetUser> studentList = userRepository.findByGroupAndUserIdNotLike(group.get(), userId);

        return studentList;
    }
}
