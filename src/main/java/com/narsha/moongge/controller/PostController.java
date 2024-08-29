package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.post.PostDTO;
import com.narsha.moongge.base.dto.post.UploadPostDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "PostController", description = "포스트 관련 API")
public class PostController {

    private final PostService postService;

    /**
     * 포스트 업로드 API
     */
    @PostMapping("/users/{userId}/posts")
    @Operation(
            summary = "포스트 업로드",
            description = "포스트 정보를 포함한 포스트를 업로드하는 API",
            parameters = @Parameter(name = "userId", description = "업로드할 유저의 ID", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "포스트 업로드 정보 및 이미지",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "multipart/form-data")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "포스트 업로드를 성공했습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> uploadPost(@PathVariable String userId,
                                                  @RequestParam("images") MultipartFile[] multipartFiles,
                                                  @RequestPart(value = "info") UploadPostDTO uploadPostDTO) {
        PostDTO res = postService.uploadPost(userId, multipartFiles, uploadPostDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_UPLOAD_POST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_UPLOAD_POST, res));
    }

    /**
     * 포스트 상세 가져오기 API
     */
    @GetMapping("/groups/{groupCode}/posts/{postId}")
    @Operation(
            summary = "포스트 상세 조회",
            description = "그룹 코드와 포스트 ID를 사용하여 포스트의 상세 정보를 조회하는 API",
            parameters = {
                    @Parameter(name = "groupCode", description = "포스트가 포함된 그룹의 코드", required = true),
                    @Parameter(name = "postId", description = "조회할 포스트의 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "포스트 상세 불러오기 성공했습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "포스트 또는 그룹을 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
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
    @Operation(
            summary = "유저의 포스트 목록 조회",
            description = "주어진 유저 ID에 의해 올린 포스트 목록을 조회하는 API",
            parameters = @Parameter(name = "userId", description = "조회할 유저의 ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 게시글 목록 불러오기 성공했습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
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
    @Operation(
            summary = "최신 포스트 목록 조회",
            description = "유저가 좋아요를 누르지 않은 최신 포스트 목록을 조회하는 API",
            parameters = @Parameter(name = "userId", description = "조회할 유저의 ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 좋아요를 누르지 않은 게시물 목록을 성공적으로 가져왔습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getMainPosts(@PathVariable(value = "userId") String userId) {
        List<PostDTO> res = postService.getMainPost(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_UNLIKED_POSTS.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_UNLIKED_POSTS, res));
    }

}