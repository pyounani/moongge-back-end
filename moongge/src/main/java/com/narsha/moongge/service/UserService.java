package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.user.*;
import com.narsha.moongge.entity.UserEntity;

public interface UserService {
    UserDTO register(UserRegisterDTO userRegisterDTO);
    Boolean checkUserId(String userId);
    UserDTO login(UserLoginDTO userLoginDTO);
    UserEntity updateProfile(UpdateUserProfileDTO updateUserProfileDTO);

    UserProfileDTO getProfile(String userId);
    String getBadgeList(String userId);
    String updateBadgeList(String userId, Integer achieveNum);
}
