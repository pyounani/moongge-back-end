package com.example.narshaback.controller;

import com.example.narshaback.dto.comment.CreateCommentDTO;
import com.example.narshaback.service.CommentService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON 형태의 결과값 반환
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/createComment")
    public String createComment(@RequestBody CreateCommentDTO createCommentDTO){
        Integer commentId = commentService.createComment(createCommentDTO);
        JsonObject obj = new JsonObject();
        obj.addProperty("commentId", commentId);

        if (commentId == null) {
            obj.addProperty("message", "댓글 작성을 실패");
        } else {
            obj.addProperty("message", "댓글 작성 성공!");
        }

        return obj.toString();
    }

}
