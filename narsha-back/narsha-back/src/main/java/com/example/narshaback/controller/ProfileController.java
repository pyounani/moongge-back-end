package com.example.narshaback.controller;

import com.example.narshaback.dto.s3.S3FileDTO;
import com.example.narshaback.dto.profile.UpdateUserProfileDTO;
import com.example.narshaback.entity.ProfileEntity;
import com.example.narshaback.service.AmazonS3Service;
import com.example.narshaback.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    private final AmazonS3Service amazonS3Service;

    @PutMapping("/updateProfile")
    public String updateProfile(@RequestPart(value="image") MultipartFile profileImage,
                                @RequestParam(value="content") String updateUserProfileDTO) throws JsonProcessingException {

        // res json object
        JsonObject obj = new JsonObject();

        // 예전 유저의 프로필 이미지 삭제

        // 이미지 등록
        // mapper
        ObjectMapper mapper = new ObjectMapper();
        UpdateUserProfileDTO mapperUpdateUserProfileDTO = mapper.readValue(updateUserProfileDTO, UpdateUserProfileDTO.class);

        S3FileDTO uploadFiles = amazonS3Service.uploadFile(profileImage);

        mapperUpdateUserProfileDTO.setProfileImage(uploadFiles.getUploadFileUrl());

        // 정보 업데이트
        ProfileEntity profile = profileService.updateProfile(mapperUpdateUserProfileDTO);

        if (profile == null) {
            obj.addProperty("id", "null");
            obj.addProperty("message", "프로필 수정 실패");
        } else {
            obj.addProperty("id", profile.getId());
            obj.addProperty("message", "프로필 수정 성공!");
        }

        return obj.toString();
    }
}
