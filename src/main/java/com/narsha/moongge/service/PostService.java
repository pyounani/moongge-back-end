package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.post.PostDTO;
import com.narsha.moongge.base.dto.post.UploadPostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostDTO uploadPost(String userId, MultipartFile[] multipartFiles, UploadPostDTO uploadPostDTO);
    PostDTO getPostDetail(String groupCode, Integer postId);
    List<PostDTO> getUserPost(String userId);
    List<PostDTO> getMainPost(String userId);

}
