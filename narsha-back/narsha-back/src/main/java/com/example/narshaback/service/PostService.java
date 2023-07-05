package com.example.narshaback.service;

import com.example.narshaback.base.dto.post.UploadPostDTO;

import com.example.narshaback.base.projection.post.GetPostDetail;
import com.example.narshaback.base.projection.post.GetUserPost;

import java.util.List;

public interface PostService {
    Integer uploadPost(UploadPostDTO uploadPostDTO);
    List<GetUserPost> getUserPost(String groupCode);

    GetPostDetail getPostDetail(Integer postId, String groupCode, String userId);

}
