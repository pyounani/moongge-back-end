package com.example.narshaback.service;

import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.dto.user.UpdateUserProfileDTO;
import com.example.narshaback.base.dto.user.UserLoginDTO;
import com.example.narshaback.base.dto.user.UserRegisterDTO;
import com.example.narshaback.base.exception.*;
import com.example.narshaback.base.projection.user.GetUser;
import com.example.narshaback.base.projection.user.GetUserProfile;
import com.example.narshaback.base.exception.GroupNotFoundException;

import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.base.code.ErrorCode;
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
    public GetUserProfile register(UserRegisterDTO userRegisterDTO) {
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
            UserEntity createUser = userRepository.save(user);
            return EntityToProjectionUser(createUser);
        }
    }

    // 로그인
    @Override
    public GetUserProfile login(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> findUser = userRepository.findByUserId(userLoginDTO.userId);

        // 아이디가 존재하는지 확인
        if(!findUser.isPresent()) throw new LoginIdNotFoundException(ErrorCode.USERID_NOT_FOUND);
        // 비밀번호가 같은지 확인
        else if(!findUser.get().getPassword().equals(userLoginDTO.password)) throw new LoginPasswordNotMatchException(ErrorCode.PASSWORD_NOT_MATCH);

        // 아이디, 비밀번호가 일치
        GetUserProfile userProfile = EntityToProjectionUser(findUser.get());

        return userProfile;
    }

    @Override
    public UserEntity joinUser(JoinGroupDTO joinGroupDTO) {
        if (!groupRepository.findByGroupCode(joinGroupDTO.getGroupCode()).isPresent())
            throw new GroupNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);

        // set group code
        Optional<UserEntity> user = userRepository.findByUserId(joinGroupDTO.getUserId());
        Optional<GroupEntity> group = groupRepository.findByGroupCode(joinGroupDTO.getGroupCode());

        if(!user.isPresent()){
            throw new UserIdNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        else if(!group.isPresent())
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
        profile.get().setNikname(updateUserProfileDTO.getNikname());

        return userRepository.save(profile.get());
    }

    // 프로필 가져오기
    @Override
    public Optional<UserEntity> getProfile(String userId) {
        Optional<UserEntity> profile = userRepository.findById(userId);

        // 프로필이 존재하지 않는 경우(잘못된 userId 입력)
        if(!profile.isPresent())
            throw new ProfileNotFoundException(ErrorCode.PROFILE_NOT_FOUND);

        return profile;
    }

    //뱃지 리스트 가져오기
    @Override
    public String getBadgeList(String userId) {
        Optional<UserEntity> profile = userRepository.findByUserId(userId);

        // 뱃지리스트(프로필)가 존재하지 않는 경우(잘못된 userId 입력)
        if(!profile.isPresent())
            throw new ProfileNotFoundException(ErrorCode.PROFILE_NOT_FOUND);

        String badgeList = profile.get().getBadgeList();

        return badgeList;
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
    public List<GetUser> getStudentList(String GroupId) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(GroupId);
        if(!group.isPresent())
            throw new GroupCodeNotFoundException(ErrorCode.GROUPCODE_NOT_FOUND);

        List<GetUser> studentList = userRepository.findByGroupCode(group.get());

        return studentList;
    }

    private GetUserProfile EntityToProjectionUser(UserEntity findUser){
        GetUserProfile userProfile = new GetUserProfile() {
            @Override
            public String getUserId() {
                return findUser.getUserId();
            }

            @Override
            public GroupEntity getGroupCode() {
                return findUser.getGroupCode();
            }

            @Override
            public String getUserName() {
                return findUser.getUserName();
            }

            @Override
            public String getUserType(){
                return findUser.getUserType();
            }

            @Override
            public String getNikName() {
                return  findUser.getNikname();
            }

            @Override
            public String getProfileImage() {
                return  findUser.getProfileImage();
            }

            @Override
            public String getBirth() {
                return  findUser.getBirth();
            }

            @Override
            public String getIntro() {
                return  findUser.getIntro();
            }

            @Override
            public String getBadgeList() {
                return  findUser.getBadgeList();
            }
        };

        return userProfile;
    }
}
