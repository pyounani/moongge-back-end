package com.example.narshaback.service;

import com.example.narshaback.base.dto.comment.CreateCommentDTO;
import com.example.narshaback.base.projection.comment.GetComment;
import com.example.narshaback.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Integer createComment(CreateCommentDTO createCommentDTO);

    List<GetComment> getCommentList(Integer postId);

    String createAIComment(Integer postId);

    Optional<GetComment> getRecentComment(Integer postId);

}
