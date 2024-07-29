package com.narsha.moongge.controller;

import com.narsha.moongge.base.code.ResponseCode;
import com.narsha.moongge.base.dto.comment.CreateCommentDTO;
import com.narsha.moongge.base.dto.response.ResponseDTO;
import com.narsha.moongge.base.projection.comment.GetComment;
import com.narsha.moongge.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Integer commentId = commentService.createComment(groupCode, postId, createCommentDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_COMMENT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_COMMENT, commentId));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getCommentList(@RequestParam(value = "postId") Integer postId){
        List<GetComment> commentList = commentService.getCommentList(postId);
        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_COMMENT_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_COMMENT_LIST, commentList));

    }

    @GetMapping("/create/chat")
    public ResponseEntity<ResponseDTO> createAIComment(@RequestParam(value = "postId") Integer postId){
        String commentId = commentService.createAIComment(postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_COMMENT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_CREATE_COMMENT, commentId));
    }

    @GetMapping("/recent")
    public ResponseEntity<ResponseDTO> getRecentComment(@RequestParam(value = "postId") Integer postId){
        Optional<GetComment> recentComment = commentService.getRecentComment(postId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_RECENT_COMMENT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_RECENT_COMMENT, recentComment));
    }

    @GetMapping("/count")
    public ResponseEntity<ResponseDTO> getCommentCount(@RequestParam(value = "userId") String userId){

        Long commentCount = commentService.countComment(userId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_COMMENT_COUNT.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_COMMENT_COUNT, commentCount));
    }
}
