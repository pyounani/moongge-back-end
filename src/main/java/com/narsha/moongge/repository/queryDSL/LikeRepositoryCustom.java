package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.LikeEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;

import java.util.List;

public interface LikeRepositoryCustom {
    List<LikeEntity> findLikesWithUserByPost(PostEntity post);
    Boolean existsPostWithMinLikesByUser(UserEntity user, Long minLikes);
}

