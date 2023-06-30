package com.example.narshaback.service;

import com.example.narshaback.dto.comment.CreateCommentDTO;
import com.example.narshaback.entity.CommentEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.User_Group;
import com.example.narshaback.projection.comment.GetComment;
import com.example.narshaback.repository.CommentRepository;
import com.example.narshaback.repository.PostRepository;
import com.example.narshaback.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserGroupRepository userGroupRepository;

    @Override
    public Integer createComment(CreateCommentDTO createCommentDTO) {
        Optional<User_Group> user_group = userGroupRepository.findByUserGroupId(createCommentDTO.getUserGroupId());
        Optional<PostEntity> findPost = postRepository.findByPostIdAndUserGroupId(createCommentDTO.getPostId(), user_group.get());

        CommentEntity comment = CommentEntity.builder()
                .postId(findPost.get())
                .userGroupId(user_group.get())
                .content(createCommentDTO.getContent())
                .build();

        return commentRepository.save(comment).getCommentId();
    }

    @Override
    public List<GetComment> getCommentList(Integer postId, Integer userGroupId) {
        Optional<User_Group> user_group = userGroupRepository.findByUserGroupId(userGroupId);
        Optional<PostEntity> findPost = postRepository.findByPostIdAndUserGroupId(postId, user_group.get());

        List<GetComment> commentList = commentRepository.findByPostIdAndUserGroupId(findPost.get(), user_group.get());

        return commentList;
    }
}
