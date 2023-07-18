package com.example.narshaback.service;

import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.dto.user.UpdateUserProfileDTO;
import com.example.narshaback.base.dto.user.UserLoginDTO;
import com.example.narshaback.base.dto.user.UserRegisterDTO;
import com.example.narshaback.base.projection.notice.GetNotice;
import com.example.narshaback.base.projection.user.GetUser;
import com.example.narshaback.base.projection.user.GetUserInGroup;
import com.example.narshaback.base.projection.user.GetUserProfile;

//import com.example.narshaback.base.dto.user.UserTypeReturnDTO;
import com.example.narshaback.entity.UserEntity;
import org.apache.catalina.User;

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

    List<GetUser> getStudentList(String GroupId);

}
