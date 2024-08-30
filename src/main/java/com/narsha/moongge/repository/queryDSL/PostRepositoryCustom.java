package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.PostEntity;

import java.util.Optional;

public interface PostRepositoryCustom {
    Optional<PostEntity> findByPostIdAndGroupWithWriter(Integer postId, GroupEntity group);
}
