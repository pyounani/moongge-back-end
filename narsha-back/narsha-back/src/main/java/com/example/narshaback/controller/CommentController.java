package com.example.narshaback.controller;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.code.ResponseCode;
import com.example.narshaback.base.dto.comment.CreateCommentDTO;
import com.example.narshaback.base.dto.response.ResponseDTO;
import com.example.narshaback.base.exception.EmptyCommentContentException;
import com.example.narshaback.base.exception.GroupCodeNotFoundException;
import com.example.narshaback.base.exception.PostNotFoundException;
import com.example.narshaback.base.projection.comment.GetComment;
import com.example.narshaback.entity.CommentEntity;
import com.example.narshaback.service.CommentService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createComment(@RequestBody CreateCommentDTO createCommentDTO){
        Integer commentId = commentService.createComment(createCommentDTO);

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
