package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.CommentEntity;
import com.narsha.moongge.entity.PostEntity;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentEntity> findCommentsWithUserByPost(PostEntity post);
}

