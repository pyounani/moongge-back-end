package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.post.PostDTO;
import com.narsha.moongge.base.dto.post.UploadPostDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.service.PostService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    /**
     * 포스트 업로드 API
     */
    @PostMapping("/groups/{groupCode}/posts")
    public ResponseEntity<ResponseDTO> uploadPost(@PathVariable String groupCode,
                                                  @RequestParam("images") MultipartFile[] multipartFiles,
                                                  @RequestPart(value="info") UploadPostDTO uploadPostDTO) {
        PostDTO res = postService.uploadPost(groupCode, multipartFiles, uploadPostDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPLOAD_POST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPLOAD_POST, res));
    }

    /**
     * 포스트 상세 가져오기 API
     */
    @GetMapping("/groups/{groupCode}/posts/{postId}")
    public ResponseEntity<ResponseDTO> getPost(@PathVariable(value = "groupCode") String groupCode,
                                               @PathVariable(value = "postId") Integer postId) {
        PostDTO res = postService.getPostDetail(groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_DETAIL_POST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_DETAIL_POST, res));
    }

    /**
     * 유저가 올린 포스트 목록 API
     */
    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<ResponseDTO> getUserPostList(@PathVariable(value = "userId") String userId) {
        List<PostDTO> res = postService.getUserPost(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_POST_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_POST_LIST, res));
    }

    /**
     * 유저가 좋아요를 누르지 않은 최신 포스트 목록 API (메인 페이지 포스트 목록)
     */
    @GetMapping("/users/{userId}/posts/main-list")
    public ResponseEntity<ResponseDTO> getMainPosts(@PathVariable(value = "userId")String userId) {
        List<PostDTO> res = postService.getMainPost(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_UNLIKED_POSTS.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_UNLIKED_POSTS, res));
    }

}
