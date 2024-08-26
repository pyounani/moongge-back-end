package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.user.*;
import com.narsha.moongge.entity.UserType;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDTO register(UserRegisterDTO userRegisterDTO, UserType userType);
    Boolean checkUserId(String userId);
    UserDTO login(UserLoginDTO userLoginDTO);
    UserProfileDTO updateProfile(String userId, MultipartFile multipartFile, UpdateUserRequestDTO updateUserRequestDTO);
    UserProfileDTO getProfile(String userId);
    String getBadgeList(String userId);
    String updateBadgeList(String userId, Integer achieveNum);
}
