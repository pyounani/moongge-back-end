package com.example.narshaback.controller;

import com.example.narshaback.dto.s3.S3FileDTO;
import com.example.narshaback.dto.post.UploadPostDTO;
import com.example.narshaback.service.AmazonS3Service;
import com.example.narshaback.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import com.example.narshaback.projection.post.GetUserPost;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    private final AmazonS3Service amazonS3Service;

    @PostMapping("/uploadPost")
    public String uploadPost(@RequestParam(value = "fileType") String fileType, @RequestPart("images") List<MultipartFile> multipartFiles,
                             @RequestParam(value = "info") String uploadPostDTO) throws JsonProcessingException {

        // mapper
        ObjectMapper mapper = new ObjectMapper();
        UploadPostDTO mapperUploadPostDTO = mapper.readValue(uploadPostDTO, UploadPostDTO.class);

        // S3에 이미지 저장
        List<S3FileDTO> uploadFiles = amazonS3Service.uploadFiles(fileType, multipartFiles);

        // S3에서 받은 URL String Array
        List<String> imageUrlArray = new ArrayList<>();

        for (S3FileDTO url : uploadFiles) {
            imageUrlArray.add(url.getUploadFileUrl());
        }

        mapperUploadPostDTO.setImageArray(imageUrlArray.toString());


        // 포스팅 내용 + 이미지 배열 저장
        Integer res = postService.uploadPost(mapperUploadPostDTO);
        JsonObject obj = new JsonObject();

        obj.addProperty("postId", res);
        // 저장 여부 확인
        if (res == null) {
            obj.addProperty("success", false);
            obj.addProperty("message", "포스트 업로드 실패");
        } else {
            obj.addProperty("success", true);
            obj.addProperty("success", "포스트 업로드 완료!");
        }

        return obj.toString();
    }

    @GetMapping("/getUserPostList")
    public List<GetUserPost> getUserPostList(@RequestParam(value = "userId") String userId) {
        List<GetUserPost> res = postService.getUserPost(userId);

        return res;
    }
}
