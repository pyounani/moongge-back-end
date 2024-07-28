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
    PostDTO uploadPost(String groupCode, MultipartFile[] multipartFiles, UploadPostDTO uploadPostDTO);
    PostDTO getPostDetail(String groupCode, Integer postId);
    List<GetMainPost> getMainPost(String userId, String groupCode);

    List<PostDTO> getUserPost(String userId);

}
