package com.example.narshaback.service;

import com.example.narshaback.dto.UserRegisterDTO;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 생성자 작성 생략
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
}
