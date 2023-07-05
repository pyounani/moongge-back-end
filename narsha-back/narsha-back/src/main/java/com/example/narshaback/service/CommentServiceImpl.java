package com.example.narshaback.service;

import com.example.narshaback.base.dto.comment.CreateCommentDTO;
import com.example.narshaback.entity.CommentEntity;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.base.projection.comment.GetComment;
import com.example.narshaback.repository.CommentRepository;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final GroupRepository groupRepository;


    @Override
    public Integer createComment(CreateCommentDTO createCommentDTO) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(createCommentDTO.getGroupCode());
        PostEntity findPost = postRepository.findByPostIdAndGroupCode(createCommentDTO.getPostId(), group.get()).orElse(null);

        CommentEntity comment = CommentEntity.builder()
                .postId(findPost)
                .groupCode(group.get())
                .content(createCommentDTO.getContent())
                .build();

        return commentRepository.save(comment).getCommentId();
    }

    @Override
    public List<GetComment> getCommentList(Integer postId, String groupCode) {
        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupCode);
        Optional<PostEntity> findPost = postRepository.findByPostIdAndGroupCode(postId, group.get());

        List<GetComment> commentList = commentRepository.findByPostIdAndGroupCode(findPost.get(), group.get());

        return commentList;
    }
}
