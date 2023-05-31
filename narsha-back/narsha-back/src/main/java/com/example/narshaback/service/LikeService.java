package com.example.narshaback.service;

import com.example.narshaback.dto.like.CreateLikeDTO;
import com.example.narshaback.projection.like.GetLikeList;

import java.util.List;

public interface LikeService {
    Integer createLike(CreateLikeDTO createLikeDTO);
    List<GetLikeList> getLikeList(Integer postId);
}

