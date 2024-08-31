package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.PostEntity;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

public interface PostRepositoryCustom {
    Optional<PostEntity> findByPostIdAndGroupWithWriter(Integer postId, GroupEntity group);
    List<PostEntity> getMainPost(String userId, GroupEntity group, LocalDateTime startTime, LocalDateTime endTime);
}
