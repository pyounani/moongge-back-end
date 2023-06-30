package com.example.narshaback.service;

import com.example.narshaback.dto.like.CreateLikeDTO;
import com.example.narshaback.entity.LikeEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.projection.like.GetLikeList;
import com.example.narshaback.repository.LikeRepository;
import com.example.narshaback.repository.PostRepository;
import com.example.narshaback.repository.UserGroupRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;

    private final PostRepository postRepository;

    private final UserGroupRepository userGroupRepository;

    @Override
    public Integer createLike(CreateLikeDTO createLikeDTO) {
        Optional<User_Group> user_group = userGroupRepository.findByUserGroupId(createLikeDTO.getUserGroupId());
        Optional<PostEntity> findPost = postRepository.findByPostIdAndUserGroupId(createLikeDTO.getPostId(), user_group.get());

        LikeEntity like = LikeEntity.builder()
                .postId(findPost.get())
                .userGroupId(user_group.get())
                .build();

        return likeRepository.save(like).getLikeId();
    }

    @Override
    public List<GetLikeList> getLikeList(Integer postId, Integer userGroupId) {
        Optional<User_Group> user_group = userGroupRepository.findByUserGroupId(userGroupId);
        Optional<PostEntity> findPost = postRepository.findByPostIdAndUserGroupId(postId, user_group.get());

        List<GetLikeList> likeList = likeRepository.findByPostIdAndUserGroupId(findPost.get(), user_group.get());

        return likeList;
    }
}
