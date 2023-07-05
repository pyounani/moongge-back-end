package com.example.narshaback.service;

import com.example.narshaback.base.dto.like.CreateLikeDTO;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.LikeEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.base.projection.like.GetLikeList;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.LikeRepository;
import com.example.narshaback.repository.PostRepository;
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

    @Override
    public Integer createLike(CreateLikeDTO createLikeDTO) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(createLikeDTO.getGroupCode());
        Optional<PostEntity> findPost = postRepository.findByPostIdAndGroupCode(createLikeDTO.getPostId(), group.get());

        LikeEntity like = LikeEntity.builder()
                .postId(findPost.get())
                .groupCode(group.get())
                .build();

        return likeRepository.save(like).getLikeId();
    }

    @Override
    public List<GetLikeList> getLikeList(Integer postId, String groupCode) {
        Optional<GroupEntity> user_group = groupRepository.findByGroupCode(groupCode);
        Optional<PostEntity> findPost = postRepository.findByPostIdAndGroupCode(postId, user_group.get());

        List<GetLikeList> likeList = likeRepository.findByPostIdAndGroupCode(findPost.get(), user_group.get());

        return likeList;
    }
}
