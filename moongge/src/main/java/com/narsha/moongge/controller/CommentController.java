package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.comment.CommentDTO;
import com.narsha.moongge.base.dto.comment.CreateCommentDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // JSON 형태의 결과값 반환
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성하기 API
     */
    @PostMapping("/groups/{groupCode}/posts/{postId}/comments")
    public ResponseEntity<ResponseDTO> createComment(@PathVariable String groupCode,
                                                     @PathVariable Integer postId,
                                                     @Valid @RequestBody CreateCommentDTO createCommentDTO){
        Integer res = commentService.createComment(groupCode, postId, createCommentDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_COMMENT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_COMMENT, res));
    }

    /**
     * 댓글 목록 불러오기 API
     */
    @GetMapping("/groups/{groupCode}/posts/{postId}/comments")
    public ResponseEntity<ResponseDTO> getCommentList(@PathVariable String groupCode,
                                                      @PathVariable Integer postId){
        List<CommentDTO> res = commentService.getCommentList(groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_COMMENT_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_COMMENT_LIST, res));
    }

    /**
     * 최신 댓글 1개 겨져오기 API
     */
    @GetMapping("/groups/{groupCode}/posts/{postId}/comments/recent")
    public ResponseEntity<ResponseDTO> getRecentComment(@PathVariable String groupCode,
                                                        @PathVariable Integer postId){
        CommentDTO res = commentService.getRecentComment(groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_RECENT_COMMENT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_RECENT_COMMENT, res));
    }

    /**
     * 특정 포스트 댓글 갯수 가져오기 API
     */
    @GetMapping("/groups/{groupCode}/posts/{postId}/comments/count")
    public ResponseEntity<ResponseDTO> getCommentCount(@PathVariable String groupCode,
                                                       @PathVariable Integer postId){
        Long res = commentService.countComment(groupCode, postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_COMMENT_COUNT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_COMMENT_COUNT, res));
    }

    @GetMapping("/create/chat")
    public ResponseEntity<ResponseDTO> createAIComment(@RequestParam(value = "postId") Integer postId){
        String commentId = commentService.createAIComment(postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_COMMENT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_COMMENT, commentId));
    }

}
