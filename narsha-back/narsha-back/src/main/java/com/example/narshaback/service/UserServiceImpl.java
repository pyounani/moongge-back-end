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
    public String register(UserRegisterDTO userRegisterDTO) {
        // 중복된 아이디 존재 여부 확인
        UserEntity findUser = userRepository.findByUserId(userRegisterDTO.getUserId());

        if(findUser == null){
            // 유저 생성
            UserEntity user = UserEntity.builder()
                    .userId(userRegisterDTO.getUserId())
                    .userType(userRegisterDTO.getUserType())
                    .password(userRegisterDTO.getPassword())
                    .name(userRegisterDTO.getName())
                    .nikname(userRegisterDTO.getNikname())
                    .build();
            return userRepository.save(user).getUserId();
        } else return null;

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
