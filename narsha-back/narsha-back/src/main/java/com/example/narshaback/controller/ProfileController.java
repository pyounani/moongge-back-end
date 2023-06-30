package com.example.narshaback.controller;

import com.example.narshaback.base.dto.s3.S3FileDTO;
import com.example.narshaback.base.dto.profile.UpdateUserProfileDTO;
import com.example.narshaback.entity.ProfileEntity;
import com.example.narshaback.service.AmazonS3Service;
import com.example.narshaback.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    private final AmazonS3Service amazonS3Service;

    @PutMapping("/update")
    public String updateProfile(@RequestParam(value="image", required = false) MultipartFile profileImage,
                                @RequestParam(value="content") String updateUserProfileDTO) throws JsonProcessingException {

        // res json object
        JsonObject obj = new JsonObject();

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
        ProfileEntity profile = profileService.updateProfile(mapperUpdateUserProfileDTO);

        if (profile == null) {
            obj.addProperty("id", "null");
            obj.addProperty("message", "프로필 수정 실패");
        } else {
            obj.addProperty("id", profile.getProfileId());
            obj.addProperty("message", "프로필 수정 성공!");
        }

        return obj.toString();
    }


    @GetMapping("/detail")
    public Optional<ProfileEntity> getProfile(@RequestParam(value = "profileId")Integer profileId){
        Optional<ProfileEntity> res = profileService.getProfile(profileId);

        return res;
    }

    @GetMapping("/badge-list")
    public String getBadgeList(@RequestParam(value = "profileId")Integer profileId){
        String res = profileService.getBadgeList(profileId);

        return res;
    }

    @PutMapping("/check-achieve")
    public String updateCheckAchieve(@RequestParam(value="profileId")Integer profileId, @RequestParam(value="achieveNum")Integer achNum){
        String res = profileService.updateBadgeList(profileId, achNum);
        return res;
    }
}
