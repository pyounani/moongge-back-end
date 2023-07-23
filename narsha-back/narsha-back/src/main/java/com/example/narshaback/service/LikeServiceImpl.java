package com.example.narshaback.service;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.dto.like.CreateLikeDTO;
import com.example.narshaback.base.exception.*;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.LikeEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.base.projection.like.GetLikeList;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.LikeRepository;
import com.example.narshaback.repository.PostRepository;
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
    private final GroupRepository groupRepository;

    private final UserRepository userRepository;

    @Override
    public Integer createLike(CreateLikeDTO createLikeDTO) {

        Optional<UserEntity> user = userRepository.findByUserId(createLikeDTO.getUserId());
        if(!user.isPresent()) {
            throw new UserNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        Optional<PostEntity> post = postRepository.findByPostId(createLikeDTO.getPostId());
        // 게시물이 존재하지 않은 경우
        if (!post.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
        }

        Optional<GroupEntity> group = groupRepository.findByGroupCode(createLikeDTO.getGroupCode());
        if(!group.isPresent()){
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
        }

        LikeEntity like = LikeEntity.builder()
                .postId(post.get())
                .groupCode(group.get())
                .userId(user.get())
                .build();

        return likeRepository.save(like).getLikeId();
    }


    @Override
    public List<GetLikeList> getLikeList(Integer postId) {

        Optional<PostEntity> findPost = postRepository.findByPostId(postId);
        // 게시물이 존재하지 않은 경우
        if (!findPost.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
        }

        List<GetLikeList> likeList = likeRepository.findByPostId(findPost.get());

        return likeList;
    }
}
