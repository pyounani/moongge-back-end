package com.example.narshaback.service;

import com.example.narshaback.base.dto.like.CreateLikeDTO;
import com.example.narshaback.base.projection.like.GetLikeList;

import java.util.List;

public interface LikeService {
    Integer createLike(CreateLikeDTO createLikeDTO);
    List<GetLikeList> getLikeList(Integer postId);
    Boolean checkLikePost(String userId, String groupCode, Integer postId);
    String deleteLike(String userId, String groupCode, Integer postId);
    Long countLike(String groupCode, Integer postId);

    Boolean checkTenLikes(String userId, String groupCode);
}

