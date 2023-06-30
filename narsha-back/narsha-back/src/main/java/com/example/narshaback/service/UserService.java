package com.example.narshaback.service;

import com.example.narshaback.dto.user.UserLoginDTO;
import com.example.narshaback.dto.user.UserRegisterDTO;
import com.example.narshaback.dto.user.UserTypeReturnDTO;
import com.example.narshaback.entity.UserEntity;

public interface UserService {
    String register(UserRegisterDTO userRegisterDTO);
    UserEntity login(UserLoginDTO userLoginDTO);
    String userType(UserTypeReturnDTO userTypeReturnDTO);

    Boolean checkUserId(String userId);
}
