package com.example.narshaback.service;

import com.example.narshaback.dto.like.CreateLikeDTO;
import com.example.narshaback.entity.LikeEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.repository.LikeRepository;
import com.example.narshaback.repository.PostRepository;
import com.example.narshaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Override
    public Integer createLike(CreateLikeDTO createLikeDTO) {
        UserEntity findUser = userRepository.findByUserId(createLikeDTO.getUserId());
        Optional<PostEntity> findPost = postRepository.findById(createLikeDTO.getPostId());

        LikeEntity like = LikeEntity.builder()
                .likePost(findPost.get())
                .likeUser(findUser)
                .build();

        return likeRepository.save(like).getId();
    }
}
