package com.narsha.moongge.repository;

import com.narsha.moongge.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 최신_댓글_1개_가져오기() {

        // given
        UserEntity user1 = createUser();
        GroupEntity group = createGroup(user1);

        UserEntity user2 = createUser();
        joinGroup(user2, group);

        PostEntity post = createPost(user1, group);

        createComment(user1, group, post);
        CommentEntity createCommentEntity = createComment(user2, group, post);

        // when
        Optional<CommentEntity> findComment = commentRepository.findTopCommentWithUserByPost(post);

        // then
        assertTrue(findComment.isPresent(), "최신 댓글을 가져오는 데 실패했습니다.");
        CommentEntity comment = findComment.get();

        assertEquals(createCommentEntity.getCommentId(), comment.getCommentId(),
                "댓글 ID가 일치하지 않습니다.");
        assertEquals(createCommentEntity.getUser(), comment.getUser(),
                "댓글 작성자 정보가 일치하지 않습니다.");
        assertEquals(createCommentEntity.getGroup(), comment.getGroup(),
                "댓글 그룹 정보가 일치하지 않습니다.");
        assertEquals(createCommentEntity.getPost(), comment.getPost(),
                "댓글이 속한 포스트 정보가 일치하지 않습니다.");
        assertEquals(createCommentEntity.getContent(), comment.getContent(),
                "댓글 내용이 일치하지 않습니다.");
    }

    private CommentEntity createComment(UserEntity user, GroupEntity group, PostEntity post) {
        CommentEntity comment = CommentEntity.builder()
                .user(user)
                .group(group)
                .post(post)
                .content("content")
                .build();

        return commentRepository.save(comment);
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
                .userType(UserType.TEACHER)
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

        user.updateGroup(savedGroup);

        return savedGroup;
    }

    private void joinGroup(UserEntity user, GroupEntity group) {
        user.updateGroup(group);
    }
}
