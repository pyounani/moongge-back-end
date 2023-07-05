package com.example.narshaback.service;

import com.example.narshaback.base.dto.like.CreateLikeDTO;
import com.example.narshaback.base.projection.like.GetLikeList;

import java.util.List;

public interface LikeService {
    Integer createLike(CreateLikeDTO createLikeDTO);
    List<GetLikeList> getLikeList(Integer postId, String groupCode);
}

