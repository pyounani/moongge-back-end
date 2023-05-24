package com.example.narshaback.service;

import com.example.narshaback.dto.user.UserLoginDTO;
import com.example.narshaback.dto.user.UserRegisterDTO;
import com.example.narshaback.dto.user.UserTypeReturnDTO;

public interface UserService {
    String register(UserRegisterDTO userRegisterDTO);
    Integer login(UserLoginDTO userLoginDTO);
    String userType(UserTypeReturnDTO userTypeReturnDTO);
}
