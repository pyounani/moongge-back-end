package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.comment.CommentDTO;
import com.narsha.moongge.base.dto.comment.CreateCommentDTO;
import com.narsha.moongge.base.projection.comment.GetComment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Integer createComment(String groupCode, Integer postId, CreateCommentDTO createCommentDTO);

    List<CommentDTO> getCommentList(String groupCode, Integer postId);

    String createAIComment(Integer postId);

    Optional<GetComment> getRecentComment(Integer postId);
    Long countComment(String userId);
}
