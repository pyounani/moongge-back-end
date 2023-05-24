package com.example.narshaback.service;

import com.example.narshaback.dto.post.UploadPostDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.PostRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;

    @Override
    public Integer uploadPost(UploadPostDTO uploadPostDTO) {
        // 해당 그룹 찾기
        GroupEntity group = groupRepository.findByGroupCode(uploadPostDTO.getGroupId());

        // 해당 유저 찾기
        UserEntity user = userRepository.findByUserId(uploadPostDTO.getWriter());

        PostEntity post = PostEntity.builder()
                .content(uploadPostDTO.getContent())
                .imageArray(uploadPostDTO.getImageArray())
                .groupId(group)
                .writer(user)
                .build();
        PostEntity uploadPost = postRepository.save(post);
        if (uploadPost == null) return null;
        return uploadPost.getId();
    }
}
