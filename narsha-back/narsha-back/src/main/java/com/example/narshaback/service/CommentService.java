package com.example.narshaback.service;

import com.example.narshaback.base.dto.comment.CreateCommentDTO;
import com.example.narshaback.base.projection.comment.GetComment;

import java.util.List;

public interface CommentService {
    Integer createComment(CreateCommentDTO createCommentDTO);

    List<GetComment> getCommentList(Integer postId);

    Integer createAIComment(Integer postId);

}
