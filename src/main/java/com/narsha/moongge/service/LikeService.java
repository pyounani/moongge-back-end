package com.narsha.moongge.service;


import com.narsha.moongge.base.dto.like.DeleteLikeDTO;
import com.narsha.moongge.base.dto.like.LikeDTO;

import java.util.List;

public interface LikeService {
    Integer createLike(String userId, Integer postId);
    List<LikeDTO> getLikeList(String userId, Integer postId);
    LikeDTO deleteLike(String userId, Integer postId, DeleteLikeDTO deleteLikeDTO);

    Boolean checkLikePost(String userId, String groupCode, Integer postId);
    Long countLike(String groupCode, Integer postId);
    Boolean receiveTenLikes(String groupCode, String userId);
    Boolean giveTenLikes(String groupCode, String userId);
}

