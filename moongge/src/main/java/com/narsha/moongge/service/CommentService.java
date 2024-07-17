package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.comment.CreateCommentDTO;
import com.narsha.moongge.base.projection.comment.GetComment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Integer createComment(CreateCommentDTO createCommentDTO);

    List<GetComment> getCommentList(Integer postId);

    String createAIComment(Integer postId);

    Optional<GetComment> getRecentComment(Integer postId);
    Long countComment(String userId);
}
