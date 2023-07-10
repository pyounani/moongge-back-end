package com.example.narshaback.service;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.dto.comment.CreateCommentDTO;
import com.example.narshaback.base.exception.EmptyCommentContentException;
import com.example.narshaback.base.exception.GroupCodeNotFoundException;
import com.example.narshaback.base.exception.PostNotFoundException;
import com.example.narshaback.base.exception.UserNotFoundException;
import com.example.narshaback.entity.CommentEntity;
import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.base.projection.comment.GetComment;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.repository.CommentRepository;
import com.example.narshaback.repository.GroupRepository;
import com.example.narshaback.repository.PostRepository;
import com.example.narshaback.repository.UserRepository;
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

    private final UserRepository userRepository;


    @Override
    public Integer createComment(CreateCommentDTO createCommentDTO) {

        Optional<UserEntity> user = userRepository.findByUserId(createCommentDTO.getUserId());
        if(!user.isPresent()) {
            throw new UserNotFoundException(ErrorCode.USERID_NOT_FOUND);
        }

        Optional<PostEntity> post = postRepository.findByPostId(createCommentDTO.getPostId());

        // 게시물이 존재하지 않은 경우
        if (!post.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
        }

        // 댓글에 아무 내용이 없을 경우
        String content = createCommentDTO.getContent();
        if(content == null || content.trim().isEmpty()) {
            throw new EmptyCommentContentException(ErrorCode.EMPTY_COMMENT_CONTENT);
        }

        CommentEntity comment = CommentEntity.builder()
                .postId(post.get())
                .userId(user.get())
                .content(createCommentDTO.getContent())
                .build();

        return commentRepository.save(comment).getCommentId();
    }

    @Override
    public List<GetComment> getCommentList(Integer postId) {

        Optional<PostEntity> findPost = postRepository.findByPostId(postId);
        // 게시물이 존재하지 않은 경우
        if (!findPost.isPresent()) {
            throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
        }

        List<GetComment> commentList = commentRepository.findByPostId(findPost.get());

        return commentList;
    }
}
