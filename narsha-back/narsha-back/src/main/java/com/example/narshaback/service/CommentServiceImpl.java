package com.example.narshaback.service;

import com.example.narshaback.dto.comment.CreateCommentDTO;
import com.example.narshaback.entity.CommentEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.repository.CommentRepository;
import com.example.narshaback.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
