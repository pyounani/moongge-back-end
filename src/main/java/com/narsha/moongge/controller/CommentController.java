package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.comment.CommentDTO;
import com.narsha.moongge.base.dto.comment.CreateCommentDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.service.CommentService;
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
@Tag(name = "CommentController", description = "CommentController API")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성하기 API
     */
    @PostMapping("/users/{userId}/posts/{postId}/comments")
    @Operation(
            summary = "댓글 작성",
            description = "특정 게시글에 댓글을 작성하는 API",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "댓글 작성에 필요한 정보",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            ),
            parameters = {
                    @Parameter(name = "userId", description = "유저 ID", required = true),
                    @Parameter(name = "postId", description = "게시글 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "댓글 작성을 성공했습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "댓글 내용이 비어있습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "게시글 정보를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> createComment(@PathVariable String userId,
                                                     @PathVariable Integer postId,
                                                     @Valid @RequestBody CreateCommentDTO createCommentDTO){
        Integer res = commentService.createComment(userId, postId, createCommentDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_COMMENT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_COMMENT, res));
    }

    /**
     * 댓글 목록 불러오기 API
     */
    @GetMapping("/users/{userId}/posts/{postId}/comments")
    @Operation(
            summary = "댓글 목록 조회",
            description = "특정 게시글의 댓글 목록을 조회하는 API",
            parameters = {
                    @Parameter(name = "userId", description = "유저 ID", required = true),
                    @Parameter(name = "postId", description = "게시글 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "댓글 목록을 성공적으로 가져왔습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "게시글 정보를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getCommentList(@PathVariable String userId,
                                                      @PathVariable Integer postId){
        List<CommentDTO> res = commentService.getCommentList(userId, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_COMMENT_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_COMMENT_LIST, res));
    }

    /**
     * 최신 댓글 1개 가져오기 API
     */
    @GetMapping("/users/{userId}/posts/{postId}/comments/recent")
    @Operation(
            summary = "최신 댓글 조회",
            description = "특정 게시글의 최신 댓글 1개를 조회하는 API",
            parameters = {
                    @Parameter(name = "userId", description = "유저 ID", required = true),
                    @Parameter(name = "postId", description = "게시글 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "최신 댓글을 성공적으로 가져왔습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),                   @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "게시글 정보를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "댓글 정보를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getRecentComment(@PathVariable String userId,
                                                        @PathVariable Integer postId){
        CommentDTO res = commentService.getRecentComment(userId, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_RECENT_COMMENT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_RECENT_COMMENT, res));
    }

    /**
     * 특정 포스트 댓글 갯수 가져오기 API
     */
    @GetMapping("/users/{userId}/posts/{postId}/comments/count")
    @Operation(
            summary = "댓글 개수 조회",
            description = "특정 게시글의 댓글 개수를 조회하는 API",
            parameters = {
                    @Parameter(name = "userId", description = "유저 ID", required = true),
                    @Parameter(name = "postId", description = "게시글 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "댓글 갯수를 성공적으로 가져왔습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "아이디에 해당하는 유저를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "게시글 정보를 찾을 수 없습니다.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> getCommentCount(@PathVariable String userId,
                                                       @PathVariable Integer postId){
        Long res = commentService.countComment(userId, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_COMMENT_COUNT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_COMMENT_COUNT, res));
    }

    /**
     * AI 댓글 생성 API
     */
    @GetMapping("/create/chat")
    @Operation(
            summary = "AI 댓글 생성",
            description = "AI를 이용해 댓글을 생성하는 API",
            parameters = @Parameter(name = "postId", description = "게시글 ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "AI 댓글 생성 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseDTO> createAIComment(@RequestParam(value = "postId") Integer postId){
        String commentId = commentService.createAIComment(postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_COMMENT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_COMMENT, commentId));
    }

}
