package com.narsha.moongge.service;


import com.narsha.moongge.base.dto.like.LikeDTO;

import java.util.List;

public interface LikeService {
    Integer createLike(String userId, Integer postId);
    List<LikeDTO> getLikeList(String userId, Integer postId);
    LikeDTO deleteLike(String userId, Integer postId);
    Boolean checkLikePost(String userId, Integer postId);
    Long countLike(String userId, Integer postId);
    Boolean receiveTenLikes(String userId);
    Boolean giveTenLikes(String userId);
}

