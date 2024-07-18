package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.user.UpdateUserProfileDTO;
import com.narsha.moongge.base.dto.user.UserLoginDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
import com.narsha.moongge.base.projection.user.GetUser;
import com.narsha.moongge.base.projection.user.GetUserProfile;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.group.dto.JoinGroupDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    GetUserProfile register(UserRegisterDTO userRegisterDTO);
    GetUserProfile login(UserLoginDTO userLoginDTO);

    UserEntity joinUser(JoinGroupDTO joinGroupDTO);

    Boolean checkUserId(String userId);

    UserEntity updateProfile(UpdateUserProfileDTO updateUserProfileDTO);

    Optional<UserEntity> getProfile(String userId);

    String getBadgeList(String userId);

    String updateBadgeList(String userId, Integer achNum);

    List<GetUser> getStudentList(String GroupId, String userId);

    void saveUserFcmToken(String userId, String fcmToken);

}
