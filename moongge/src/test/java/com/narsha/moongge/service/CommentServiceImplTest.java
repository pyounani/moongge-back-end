package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.comment.CreateCommentDTO;
import com.narsha.moongge.entity.CommentEntity;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.CommentRepository;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.PostRepository;
import com.narsha.moongge.repository.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 댓글_작성하기() {

        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);

        PostEntity post = createPost(user, group);

        CreateCommentDTO createCommentDTO = CreateCommentDTO.builder()
                .groupCode(group.getGroupCode())
                .postId(post.getPostId())
                .writer(user.getUserId())
                .content("content")
                .build();

        // when
        Integer commentId = commentService.createComment(group.getGroupCode(), post.getPostId(), createCommentDTO);

        // then
        assertNotNull(commentId, "댓글 ID는 null이 아니어야 합니다.");

        Optional<CommentEntity> findComment = commentRepository.findById(commentId);
        assertTrue(findComment.isPresent());
        CommentEntity comment = findComment.get();

        assertEquals(createCommentDTO.getGroupCode(), comment.getGroup().getGroupCode());
        assertEquals(createCommentDTO.getPostId(), comment.getPost().getPostId());
        assertEquals(createCommentDTO.getWriter(), comment.getUser().getUserId());
        assertEquals(createCommentDTO.getContent(), comment.getContent());
    }


    private PostEntity createPost(UserEntity user, GroupEntity group) {
        PostEntity post = PostEntity.builder()
                .user(user)
                .group(group)
                .content("content")
                .build();

        return postRepository.save(post);
    }

    private UserEntity createUser() {
        UserEntity user = UserEntity.builder()
                .userId("userId")
                .userType("teacher")
                .password("password")
                .userName("name")
                .build();

        return userRepository.save(user);
    }

    private GroupEntity createGroup(UserEntity user) {
        GroupEntity group = GroupEntity.builder()
                .groupCode("groupCode")
                .groupName("groupName")
                .school("school")
                .grade(3)
                .groupClass(5)
                .build();

        GroupEntity savedGroup = groupRepository.save(group);

        user.setGroup(savedGroup);

        return savedGroup;
    }

}