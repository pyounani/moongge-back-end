package com.example.narshaback.controller;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.code.ResponseCode;
import com.example.narshaback.base.dto.group.JoinGroupDTO;
import com.example.narshaback.base.dto.user.UpdateUserProfileDTO;
import com.example.narshaback.base.dto.response.ResponseDTO;
import com.example.narshaback.base.dto.s3.S3FileDTO;
import com.example.narshaback.base.dto.user.UserLoginDTO;
import com.example.narshaback.base.dto.user.UserRegisterDTO;
import com.example.narshaback.base.exception.ProfileNotFoundException;
import com.example.narshaback.base.projection.user.GetUserProfile;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.service.AmazonS3Service;
import com.example.narshaback.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AmazonS3Service amazonS3Service;

    @GetMapping("/")
    public String welcome(){
        return "home";
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserRegisterDTO userRegisterDTO){
        GetUserProfile res = userService.register(userRegisterDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_REGISTER.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_REGISTER, res));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO){
        GetUserProfile res = userService.login(userLoginDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_LOGIN.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_LOGIN, res));

    }

    @GetMapping("/check-userId")
    public ResponseEntity<ResponseDTO> checkUserId(@RequestParam(value="userId")String userId){
        Boolean res = userService.checkUserId(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_LOGIN.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UNIQUE_ID, res));
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> joinGroup(@RequestBody JoinGroupDTO joinGroupDTO){
        UserEntity res = userService.joinUser(joinGroupDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_JOIN_GROUP.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_JOIN_GROUP, res));
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateProfile(@RequestParam(value="image", required = false) MultipartFile profileImage,
                                @RequestParam(value="content") String updateUserProfileDTO) throws JsonProcessingException {
        // mapper
        ObjectMapper mapper = new ObjectMapper();
        UpdateUserProfileDTO mapperUpdateUserProfileDTO = mapper.readValue(updateUserProfileDTO, UpdateUserProfileDTO.class);

        // 이미지 등록
        if (profileImage != null){
            // 예전 유저의 프로필 이미지 삭제

            // 이미지 업로드
            S3FileDTO uploadFiles = amazonS3Service.uploadFile(profileImage);
            // updateUserProfileDTO 객체에 프로필 정보 설정
            mapperUpdateUserProfileDTO.setProfileImage(uploadFiles.getUploadFileUrl());
        }

        // 정보 업데이트
        UserEntity res = userService.updateProfile(mapperUpdateUserProfileDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPDATE_PROFILE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPDATE_PROFILE, res));
    }


    @GetMapping("/detail")
    public Optional<UserEntity> getProfile(@RequestParam(value = "userId")String userId){
        Optional<UserEntity> res = userService.getProfile(userId);

        return res;
    }

    @GetMapping("/badge-list")
    public String getBadgeList(@RequestParam(value = "userId")String userId){
        String res = userService.getBadgeList(userId);

        return res;
    }

    @PutMapping("/check-achieve")
    public String updateCheckAchieve(@RequestParam(value="userId")String userId, @RequestParam(value="achieveNum")Integer achNum){
        String res = userService.updateBadgeList(userId, achNum);

        return res;
    }

}
