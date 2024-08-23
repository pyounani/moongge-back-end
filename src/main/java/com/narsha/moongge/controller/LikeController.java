package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.like.CreateLikeDTO;
import com.narsha.moongge.base.dto.like.DeleteLikeDTO;
import com.narsha.moongge.base.dto.like.LikeDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // JSON 형태의 결과값 반환
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "LikeController", description = "좋아요 관련 API")
public class LikeController {

    private final LikeService likeService;

    /**
     * 좋아요 생성하기 API
     */
    @PostMapping("/groups/{groupCode}/posts/{postId}/likes")
    @Operation(
            summary = "좋아요 생성",
            description = "특정 그룹의 포스트에 좋아요를 생성하는 API",
            parameters = {
                    @Parameter(name = "groupCode", description = "좋아요를 추가할 포스트가 속한 그룹 코드", required = true),
                    @Parameter(name = "postId", description = "좋아요를 추가할 포스트의 ID", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "좋아요 생성 정보",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "좋아요 생성 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> createLike(@PathVariable String groupCode,
                                                  @PathVariable Integer postId,
                                                  @Valid @RequestBody CreateLikeDTO createLikeDTO) {
        Integer res = likeService.createLike(groupCode, postId, createLikeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_LIKE, res));
    }

    /**
     * 특정 포스트에 좋아요 누른 유저 목록 API
     */
    @GetMapping("/groups/{groupCode}/posts/{postId}/likes/users")
    @Operation(
            summary = "좋아요 누른 유저 목록 조회",
            description = "특정 그룹의 포스트에 좋아요를 누른 유저 목록을 조회하는 API",
            parameters = {
                    @Parameter(name = "groupCode", description = "포스트가 속한 그룹 코드", required = true),
                    @Parameter(name = "postId", description = "좋아요를 누른 유저 목록을 조회할 포스트의 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "좋아요 누른 유저 목록 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getLikeList(@PathVariable String groupCode,
                                                   @PathVariable Integer postId) {
        List<LikeDTO> res = likeService.getLikeList(groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_LIKE_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_LIKE_LIST, res));
    }

    /**
     * 좋아요 취소 API
     */
    @DeleteMapping("/groups/{groupCode}/posts/{postId}/likes")
    @Operation(
            summary = "좋아요 취소",
            description = "특정 그룹의 포스트에 대한 좋아요를 취소하는 API",
            parameters = {
                    @Parameter(name = "groupCode", description = "좋아요를 취소할 포스트가 속한 그룹 코드", required = true),
                    @Parameter(name = "postId", description = "좋아요를 취소할 포스트의 ID", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "좋아요 취소 정보",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "좋아요 취소 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> deleteLike(@PathVariable String groupCode,
                                                  @PathVariable Integer postId,
                                                  @Valid @RequestBody DeleteLikeDTO deleteLikeDTO) {
        LikeDTO res = likeService.deleteLike(groupCode, postId, deleteLikeDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_DELETE_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_DELETE_LIKE, res));
    }

    /**
     * 포스트에 좋아요 여부 받아오기 API
     */
    @GetMapping("/groups/{groupCode}/users/{userId}/posts/{postId}/likes/check")
    @Operation(
            summary = "좋아요 여부 확인",
            description = "특정 그룹의 포스트에 대해 사용자가 좋아요를 눌렀는지 여부를 확인하는 API",
            parameters = {
                    @Parameter(name = "groupCode", description = "포스트가 속한 그룹 코드", required = true),
                    @Parameter(name = "userId", description = "좋아요 여부를 확인할 사용자 ID", required = true),
                    @Parameter(name = "postId", description = "좋아요 여부를 확인할 포스트의 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "좋아요 여부 확인 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> checkLike(@PathVariable String groupCode,
                                                 @PathVariable String userId,
                                                 @PathVariable Integer postId) {
        boolean res = likeService.checkLikePost(userId, groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CHECK_LIKE_POST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CHECK_LIKE_POST, res));
    }

    /**
     * 특정 포스트에 좋아요가 누른 갯수 API
     */
    @GetMapping("/groups/{groupCode}/posts/{postId}/likes/count")
    @Operation(
            summary = "좋아요 개수 조회",
            description = "특정 그룹의 포스트에 대해 좋아요의 총 개수를 조회하는 API",
            parameters = {
                    @Parameter(name = "groupCode", description = "포스트가 속한 그룹 코드", required = true),
                    @Parameter(name = "postId", description = "좋아요 개수를 조회할 포스트의 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "좋아요 개수 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> countLike(@PathVariable String groupCode,
                                                 @PathVariable Integer postId) {
        Long res = likeService.countLike(groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_COUNT_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_COUNT_LIKE, res));
    }

    /**
     * 사용자가 쓴 게시글 중 좋아요 10개가 넘는 글의 여부 API
     */
    @GetMapping("/groups/{groupCode}/users/{userId}/likes/check-receive")
    @Operation(
            summary = "10개 이상의 좋아요 받은 게시글 여부 확인",
            description = "사용자가 쓴 게시글 중에서 좋아요가 10개 이상 달린 글이 있는지 여부를 확인하는 API",
            parameters = {
                    @Parameter(name = "groupCode", description = "사용자의 게시글이 포함된 그룹 코드", required = true),
                    @Parameter(name = "userId", description = "좋아요 개수를 확인할 사용자 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "10개 이상의 좋아요 받은 게시글 여부 확인 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> checkTenLikes(@PathVariable String groupCode,
                                                     @PathVariable String userId) {
        Boolean res = likeService.receiveTenLikes(groupCode, userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_RECEIVE_TEN_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_RECEIVE_TEN_LIKE, res));
    }

    /**
     * 사용자가 좋아요를 10개 이상 달았는지 여부 API
     */
    @GetMapping("/groups/{groupCode}/users/{userId}/likes/check-give")
    @Operation(
            summary = "10개 이상의 좋아요를 달았는지 여부 확인",
            description = "사용자가 좋아요를 10개 이상 달았는지 여부를 확인하는 API",
            parameters = {
                    @Parameter(name = "groupCode", description = "사용자가 좋아요를 달았는지 확인할 그룹 코드", required = true),
                    @Parameter(name = "userId", description = "좋아요 개수를 확인할 사용자 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "10개 이상의 좋아요를 달았는지 여부 확인 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> giveTenLikes(@PathVariable String groupCode,
                                                    @PathVariable String userId){
        Boolean res = likeService.giveTenLikes(groupCode, userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GIVE_TEN_LIKE.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GIVE_TEN_LIKE, res));
    }
}
