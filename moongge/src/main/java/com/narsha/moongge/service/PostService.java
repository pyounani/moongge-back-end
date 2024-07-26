package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.post.PostDTO;
import com.narsha.moongge.base.dto.post.UploadPostDTO;
import com.narsha.moongge.base.projection.post.GetMainPost;
import com.narsha.moongge.base.projection.post.GetOneUserPost;
import com.narsha.moongge.base.projection.post.GetPostDetail;
import com.narsha.moongge.base.projection.post.GetUserPost;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostDTO uploadPost(MultipartFile[] multipartFiles, UploadPostDTO uploadPostDTO);
    List<GetUserPost> getUserPost(String groupCode);

    GetPostDetail getPostDetail(Integer postId, String groupCode, String userId);

    List<GetMainPost> getMainPost(String userId, String groupCode);

    List<GetOneUserPost> getOneUserPost(String userId);

}
