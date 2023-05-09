package com.example.narshaback.service;

import com.example.narshaback.dto.UserLoginDTO;
import com.example.narshaback.dto.UserRegisterDTO;

public interface UserService {
    String register(UserRegisterDTO userRegisterDTO);
    Integer login(UserLoginDTO userLoginDTO);
}
