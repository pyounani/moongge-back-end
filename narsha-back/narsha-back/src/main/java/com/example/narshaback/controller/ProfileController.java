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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    private final AmazonS3Service amazonS3Service;

    @PutMapping("/update")
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
