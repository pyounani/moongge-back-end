package com.example.narshaback.service;

import com.example.narshaback.dto.post.UploadPostDTO;

import com.example.narshaback.projection.post.GetPostDetail;
import com.example.narshaback.projection.post.GetUserPost;

import java.util.List;

public interface PostService {
    Integer uploadPost(UploadPostDTO uploadPostDTO);
    List<GetUserPost> getUserPost(Integer userGroupId);

    GetPostDetail getPostDetail(Integer postId, Integer groupCode);

}
