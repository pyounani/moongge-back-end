package com.example.narshaback.service;

import com.example.narshaback.dto.user.UserLoginDTO;
import com.example.narshaback.dto.user.UserRegisterDTO;
import com.example.narshaback.dto.user.UserTypeReturnDTO;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.exception.ErrorCode;
import com.example.narshaback.exception.RegisterException;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 생성자 작성 생략
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // 회원가입
    @Override
    public String register(UserRegisterDTO userRegisterDTO) {
        UserEntity findUser = userRepository.findByUserId(userRegisterDTO.getUserId());

        if(findUser != null){ // 중복된 유저 있을 때
            throw new RegisterException(ErrorCode.DUPLICATE_ID_REQUEST);
        } else {
            UserEntity user = UserEntity.builder()
                    .userId(userRegisterDTO.getUserId())
                    .userType(userRegisterDTO.getUserType())
                    .password(userRegisterDTO.getPassword())
                    .userName(userRegisterDTO.getName())
                    .build();
            return userRepository.save(user).getUserId();
        }
    }

    // 로그인
    @Override
    public Integer login(UserLoginDTO userLoginDTO) {
        // 아이디가 존재하는지 확인
        UserEntity findUser = userRepository.findByUserId(userLoginDTO.userId);

        if(findUser == null) return 1;
        // 비밀번호가 같은지 확인
        else if(!findUser.getPassword().equals(userLoginDTO.password)) return 2;

        return 3;
    }

    // return userType
    @Override
    public String userType(UserTypeReturnDTO userTypeReturnDTO) {
        UserEntity user = userRepository.findByUserId(userTypeReturnDTO.getUserId());

        return user.getUserType();
    }

    @Override
    public Boolean checkUserId(String userId) {
        UserEntity user = userRepository.findByUserId(userId);

        if (user == null) return true;
        else return false;
    }
}
