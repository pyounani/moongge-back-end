package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.comment.CommentDTO;
import com.narsha.moongge.base.dto.comment.CreateCommentDTO;

import java.util.List;

public interface CommentService {

    Integer createComment(String userId, Integer postId, CreateCommentDTO createCommentDTO);
    List<CommentDTO> getCommentList(String userId, Integer postId);
    CommentDTO getRecentComment(String groupCode, Integer postId);
    Long countComment(String userId, Integer postId);
    String createAIComment(Integer postId);
}
