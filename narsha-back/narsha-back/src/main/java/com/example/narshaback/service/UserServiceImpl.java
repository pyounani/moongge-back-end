package com.example.narshaback.service;

import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.dto.user.UpdateUserProfileDTO;
import com.example.narshaback.base.dto.user.UserLoginDTO;
import com.example.narshaback.base.dto.user.UserRegisterDTO;
import com.example.narshaback.base.exception.GroupNotFoundException;
import com.example.narshaback.base.projection.user.GetFriendsList;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.exception.LoginIdNotFoundException;
import com.example.narshaback.base.exception.LoginPasswordNotMatchException;
import com.example.narshaback.base.exception.RegisterException;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 생성자 작성 생략
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    // 회원가입
    @Override
    public String register(UserRegisterDTO userRegisterDTO) {
        Optional<UserEntity> findUser = userRepository.findByUserId(userRegisterDTO.getUserId());
        if(findUser.isPresent()){ // 중복된 유저 있을 때
            throw new RegisterException(ErrorCode.DUPLICATE_ID_REQUEST);
        } else {
            UserEntity user = UserEntity.builder()
                    .userId(userRegisterDTO.getUserId())
                    .userType(userRegisterDTO.getUserType())
                    .password(userRegisterDTO.getPassword())
                    .userName(userRegisterDTO.getName())
                    .build();
            return userRepository.save(user).getUserId();
        }
    }

    // 로그인
    @Override
    public UserEntity login(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> findUser = userRepository.findByUserId(userLoginDTO.userId);

        // 아이디가 존재하는지 확인
        if(!findUser.isPresent()) throw new LoginIdNotFoundException(ErrorCode.USERID_NOT_FOUND);
        // 비밀번호가 같은지 확인
        else if(!findUser.get().getPassword().equals(userLoginDTO.password)) throw new LoginPasswordNotMatchException(ErrorCode.PASSWORD_NOT_MATCH);

        // 아이디, 비밀번호가 일치
        return findUser.get();
    }

    @Override
    public UserEntity joinUser(JoinGroupDTO joinGroupDTO) {
        if (!groupRepository.findByGroupCode(joinGroupDTO.getGroupCode()).isPresent())
            throw new GroupNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);

        // set group code
        Optional<UserEntity> user = userRepository.findByUserId(joinGroupDTO.getUserId());
        Optional<GroupEntity> group = groupRepository.findByGroupCode(joinGroupDTO.getGroupCode());

        if(!group.isPresent())
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);

        // badgeList 생성
        List<Boolean> newBadgeList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            newBadgeList.add(false);
        }

        // profile badge update
        user.get().setBadgeList(newBadgeList.toString()); // add badgeList
        user.get().setGroupCode(group.get()); // add group code

        return userRepository.save(user.get());
    }

    @Override
    public Boolean checkUserId(String userId) {
        Optional<UserEntity> user = userRepository.findByUserId(userId);

        if (user.isPresent()) throw new RegisterException(ErrorCode.DUPLICATE_ID_REQUEST);
        return true;
    }
    //

    @Override
    public UserEntity updateProfile(UpdateUserProfileDTO updateUserProfileDTO) {

        // 특정 프로필 객체 업데이트
        Optional<UserEntity> profile = userRepository.findByUserId(updateUserProfileDTO.getUserId());
        profile.get().setProfileImage(updateUserProfileDTO.getProfileImage());
        profile.get().setBirth(updateUserProfileDTO.getBirth());
        profile.get().setIntro(updateUserProfileDTO.getIntro());
        profile.get().setNikname(updateUserProfileDTO.getNikname());

        return userRepository.save(profile.get());
    }

    @Override
    public Optional<UserEntity> getProfile(String userId) {
        Optional<UserEntity> profile = userRepository.findById(userId);

        return profile;
    }

    @Override
    public String getBadgeList(String userId) {
        Optional<UserEntity> profile = userRepository.findByUserId(userId);
        String badgeList = profile.get().getBadgeList();

        return badgeList;
    }

    @Override
    public String updateBadgeList(String userId, Integer achNum) {
        Optional<UserEntity> profile = userRepository.findByUserId(userId);
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
    public List<GetFriendsList> getFriendsList(String groupCode) {
        Optional<GroupEntity> user_group = groupRepository.findByGroupCode(groupCode);

        if(!user_group.isPresent()){
            throw new GroupNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);
        }

        List<GetFriendsList> friend = userRepository.findByGroupCode(user_group.get());

        return friend;
    }
}
