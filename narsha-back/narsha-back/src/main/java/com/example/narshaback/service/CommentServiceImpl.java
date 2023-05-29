package com.example.narshaback.service;

import com.example.narshaback.dto.comment.CreateCommentDTO;
import com.example.narshaback.entity.CommentEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.projection.comment.GetComment;
import com.example.narshaback.repository.CommentRepository;
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

    @Override
    public Integer createComment(CreateCommentDTO createCommentDTO) {
        System.out.println(createCommentDTO.getPostId());
        Optional<PostEntity> findPost = postRepository.findById(createCommentDTO.getPostId());

        CommentEntity comment = CommentEntity.builder()
                .post(findPost.get())
                .content(createCommentDTO.getContent())
                .build();

        return commentRepository.save(comment).getId();
    }

    @Override
    public List<GetComment> getCommentList(Integer postId) {
        Optional<PostEntity> findPost = postRepository.findById(postId);

        List<GetComment> commentList = commentRepository.findByPost(findPost.get());

        return commentList;
    }
}
