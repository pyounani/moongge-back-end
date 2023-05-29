package com.example.narshaback.service;

import com.example.narshaback.dto.comment.CreateCommentDTO;

public interface CommentService {
    Integer createComment(CreateCommentDTO createCommentDTO);
}
