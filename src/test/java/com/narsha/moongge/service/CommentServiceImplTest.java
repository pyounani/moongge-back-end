package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.comment.CommentDTO;
import com.narsha.moongge.base.dto.comment.CreateCommentDTO;
import com.narsha.moongge.entity.*;
import com.narsha.moongge.repository.CommentRepository;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.PostRepository;
import com.narsha.moongge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        CreateCommentDTO createCommentDTO = buildCreateCommentDTO(user, group, post);

        // when
        Integer commentId = commentService.createComment(user.getUserId(), post.getPostId(), createCommentDTO);

        // then
        assertNotNull(commentId, "댓글 ID는 null이 아니어야 합니다.");

        Optional<CommentEntity> findComment = commentRepository.findById(commentId);
        assertTrue(findComment.isPresent());
        CommentEntity comment = findComment.get();

        assertEquals(createCommentDTO.getPostId(), comment.getPost().getPostId());
        assertEquals(createCommentDTO.getWriter(), comment.getUser().getUserId());
        assertEquals(createCommentDTO.getContent(), comment.getContent());
    }
    @Test
    void 댓글_목록_불러오기() {

        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateCommentDTO createCommentDTO1 = buildCreateCommentDTO(user, group, post);
        CreateCommentDTO createCommentDTO2 = buildCreateCommentDTO(user, group, post);
        Integer commentId1 = commentService.createComment(user.getUserId(), post.getPostId(), createCommentDTO1);
        Integer commentId2 = commentService.createComment(user.getUserId(), post.getPostId(), createCommentDTO2);

        // when
        List<CommentDTO> commentList = commentService.getCommentList(user.getUserId(), post.getPostId());

        // then
        assertNotNull(commentList, "댓글 목록은 null이 아니어야 합니다.");
        assertEquals(2, commentList.size(), "댓글 목록의 크기는 2이어야 합니다.");

        assertCommentListContains(commentList, commentId1, user, createCommentDTO1, "댓글 목록에 첫 번째 댓글이 포함되어 있어야 합니다.");
        assertCommentListContains(commentList, commentId2, user, createCommentDTO2, "댓글 목록에 두 번째 댓글이 포함되어 있어야 합니다.");
    }

    @Test
    void 최신_댓글_1개_불러오기() {

        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateCommentDTO createCommentDTO = buildCreateCommentDTO(user, group, post);
        CreateCommentDTO recentCreateCommentDTO = buildCreateCommentDTO(user, group, post);
        commentService.createComment(user.getUserId(), post.getPostId(), createCommentDTO);
        Integer commentId = commentService.createComment(user.getUserId(), post.getPostId(), recentCreateCommentDTO);

        // when
        CommentDTO recentFindComment = commentService.getRecentComment(group.getGroupCode(), post.getPostId());

        // then
        assertEquals(commentId, recentFindComment.getCommentId());
        assertEquals(recentCreateCommentDTO.getContent(), recentFindComment.getContent());
        assertEquals(recentCreateCommentDTO.getWriter(), recentFindComment.getWriter());
    }

    @Test
    void 댓글_갯수_가져오기() {

        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateCommentDTO createCommentDTO1 = buildCreateCommentDTO(user, group, post);
        CreateCommentDTO createCommentDTO2 = buildCreateCommentDTO(user, group, post);
        commentService.createComment(user.getUserId(), post.getPostId(), createCommentDTO1);
        commentService.createComment(user.getUserId(), post.getPostId(), createCommentDTO2);

        // when
        Long countComments = commentService.countComment(group.getGroupCode(), post.getPostId());

        // then
        assertEquals(2, countComments);
    }

    private void assertCommentListContains(List<CommentDTO> commentList, Integer commentId1, UserEntity user, CreateCommentDTO createCommentDTO1, String message) {
        boolean containsComment1 = commentList.stream()
                .anyMatch(comment -> comment.getCommentId().equals(commentId1) &&
                        comment.getWriter().equals(user.getUserId()) &&
                        comment.getContent().equals(createCommentDTO1.getContent()));

        assertTrue(containsComment1, message);
    }

    private CreateCommentDTO buildCreateCommentDTO(UserEntity user, GroupEntity group, PostEntity post) {
        CreateCommentDTO createCommentDTO = CreateCommentDTO.builder()
                .postId(post.getPostId())
                .writer(user.getUserId())
                .content("content")
                .build();
        return createCommentDTO;
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

}