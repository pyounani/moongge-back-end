package com.example.narshaback.service;

import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.dto.user.UpdateUserProfileDTO;
import com.example.narshaback.base.dto.user.UserLoginDTO;
import com.example.narshaback.base.dto.user.UserRegisterDTO;
import com.example.narshaback.base.projection.user.GetUserProfile;
import com.example.narshaback.entity.UserEntity;

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
}
