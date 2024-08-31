package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.CommentEntity;
import com.narsha.moongge.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {
    List<CommentEntity> findCommentsWithUserByPost(PostEntity post);
    Optional<CommentEntity> findTopCommentWithUserByPost(PostEntity post);
}

