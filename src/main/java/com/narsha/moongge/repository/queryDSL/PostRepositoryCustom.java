package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.PostEntity;

import java.util.Optional;

public interface PostRepositoryCustom {
    Optional<PostEntity> findByPostIdAndGroupWithWriter(Integer postId, String groupCode);
}
