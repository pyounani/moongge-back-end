package com.narsha.moongge.service;


import com.narsha.moongge.base.dto.like.CreateLikeDTO;
import com.narsha.moongge.base.projection.like.GetLikeList;

import java.util.List;

public interface LikeService {
    Integer createLike(String groupCode, Integer postId, CreateLikeDTO createLikeDTO);
    List<GetLikeList> getLikeList(Integer postId);
    Boolean checkLikePost(String userId, String groupCode, Integer postId);
    String deleteLike(String userId, String groupCode, Integer postId);
    Long countLike(String groupCode, Integer postId);

    Boolean receiveTenLikes(String userId, String groupCode);

    Long giveTenLikes(String userId);
}

