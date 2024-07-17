package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.post.UploadPostDTO;
import com.narsha.moongge.base.projection.post.GetMainPost;
import com.narsha.moongge.base.projection.post.GetOneUserPost;
import com.narsha.moongge.base.projection.post.GetPostDetail;
import com.narsha.moongge.base.projection.post.GetUserPost;

import java.util.List;

public interface PostService {
    Integer uploadPost(UploadPostDTO uploadPostDTO);
    List<GetUserPost> getUserPost(String groupCode);

    GetPostDetail getPostDetail(Integer postId, String groupCode, String userId);

    List<GetMainPost> getMainPost(String userId, String groupCode);

    List<GetOneUserPost> getOneUserPost(String userId);

}
