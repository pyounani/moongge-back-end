package com.example.narshaback.service;

import com.example.narshaback.base.dto.post.UploadPostDTO;

import com.example.narshaback.base.projection.post.GetPostDetail;
import com.example.narshaback.base.projection.post.GetMainPost;
import com.example.narshaback.base.projection.post.GetUserPost;
import com.example.narshaback.entity.PostEntity;

import java.util.List;

public interface PostService {
    Integer uploadPost(UploadPostDTO uploadPostDTO);
    List<GetUserPost> getUserPost(String groupCode);

    GetPostDetail getPostDetail(Integer postId, String groupCode, String userId);

    List<GetMainPost> getMainPost(String userId, String groupCode);

}
