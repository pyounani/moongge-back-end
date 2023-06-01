package com.example.narshaback.service;

import com.example.narshaback.dto.post.UploadPostDTO;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.projection.post.GetPostDetail;


public interface PostService {
    Integer uploadPost(UploadPostDTO uploadPostDTO);

    GetPostDetail getPostDetail(Integer PostId);


}
