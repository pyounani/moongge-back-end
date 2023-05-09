package com.example.narshaback.service;

import com.example.narshaback.dto.UserLoginDTO;
import com.example.narshaback.dto.UserRegisterDTO;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 생성자 작성 생략
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // 회원가입
    @Override
    public String register(UserRegisterDTO userDTO) {
        UserEntity user = UserEntity.builder()
                .userId(userDTO.getUserId())
                .userType(userDTO.getUserType())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .nikname(userDTO.getNikname())
            .build();
        return userRepository.save(user).getUserId();
    }

    // 로그인
    @Override
    public Integer login(UserLoginDTO userLoginDTO) {
        // 아이디가 존재하는지 확인
        UserEntity findUser = userRepository.findByUserId(userLoginDTO.userId);

        if(findUser == null) return 1;
        // 비밀번호가 같은지 확인
        if(findUser.getPassword() != userLoginDTO.password) return 2;

        return 3;
    }
}
