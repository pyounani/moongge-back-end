package com.narsha.moongge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.post.PostDTO;
import com.narsha.moongge.base.dto.post.UploadPostDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.dto.s3.S3FileDTO;
import com.narsha.moongge.base.dto.s3.S3PathDTO;
import com.narsha.moongge.base.dto.s3.S3urlDTO;
import com.narsha.moongge.base.projection.post.GetMainPost;
import com.narsha.moongge.base.projection.post.GetOneUserPost;
import com.narsha.moongge.base.projection.post.GetPostDetail;
import com.narsha.moongge.base.projection.post.GetUserPost;
import com.narsha.moongge.service.AmazonS3Service;
import com.narsha.moongge.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    /**
     * 포스트 업로드 API
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> uploadPost(@RequestParam("images") MultipartFile[] multipartFiles,
                                                  @RequestPart(value="info") UploadPostDTO uploadPostDTO) {
        PostDTO res = postService.uploadPost(multipartFiles, uploadPostDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPLOAD_POST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPLOAD_POST, res));
    }

    @GetMapping("/user-list")
    public ResponseEntity<ResponseDTO> getUserPostList(@RequestParam(value = "groupCode") String groupCode) {

        List<GetUserPost> res = postService.getUserPost(groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_POST_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_POST_LIST, res));
    }

    @GetMapping("/detail")
    public ResponseEntity<ResponseDTO> getPost(@RequestParam(value = "postId")Integer postId,
                                                 @RequestParam(value = "groupCode")String groupCode,
                                                 @RequestParam(value = "userId")String userId) {
        GetPostDetail res =  postService.getPostDetail(postId, groupCode, userId);

//        return new ResponseEntity<>(res, HttpStatus.OK);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_DETAIL_POST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_DETAIL_POST, res));
    }

    @GetMapping("/main-list")
    public ResponseEntity<ResponseDTO> getUnlikedPosts(@RequestParam(value = "userId")String userId,
                                                       @RequestParam(value = "groupCode")String groupCode) {
        List<GetMainPost> res = postService.getMainPost(userId, groupCode);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_UNLIKED_POSTS.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_UNLIKED_POSTS, res));
    }

    @GetMapping("/user-post-list")
    public ResponseEntity<ResponseDTO> getOneUserPostList(@RequestParam(value = "userId") String userId) {

        List<GetOneUserPost> res = postService.getOneUserPost(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_POST_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_POST_LIST, res));
    }

}
