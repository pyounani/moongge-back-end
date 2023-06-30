package com.example.narshaback.service;

import com.example.narshaback.dto.comment.CreateCommentDTO;
import com.example.narshaback.projection.comment.GetComment;

import java.util.List;

public interface CommentService {
    Integer createComment(CreateCommentDTO createCommentDTO);

    List<GetComment> getCommentList(Integer postId, Integer userGroupId);
}
