package com.narsha.moongge.repository;

import com.narsha.moongge.base.dto.group.CreateGroupDTO;
import com.narsha.moongge.base.dto.user.UserRegisterDTO;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.service.GroupService;
import com.narsha.moongge.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupService groupService;

    private UserEntity user;
    private GroupEntity group;

    @BeforeEach
    void setUp() {
        user = createUser();
        group = createGroup(user);
    }

    @Test
    void testFindByGroupAndCreateAtBetweenOrderByCreateAtDesc() {
        // given
        LocalDateTime now = LocalDateTime.now();
        PostEntity post1 = createPost(group, user, "Post 1", now.minusDays(2));
        PostEntity post2 = createPost(group, user, "Post 2", now.minusDays(1));
        PostEntity post3 = createPost(group, user, "Post 3", now);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        LocalDateTime startTime = now.minusDays(3);
        LocalDateTime endTime = now.plusDays(1);

        // when
        List<PostEntity> posts = postRepository.findByGroupAndCreateAtBetweenOrderByCreateAtDesc(group, startTime, endTime);

        // then
        assertEquals(3, posts.size(), "조회된 포스트 수가 일치해야 합니다.");
        assertEquals(post3.getPostId(), posts.get(0).getPostId(), "가장 최근 포스트가 첫 번째로 나와야 합니다.");
        assertEquals(post2.getPostId(), posts.get(1).getPostId(), "두 번째 포스트가 두 번째로 나와야 합니다.");
        assertEquals(post1.getPostId(), posts.get(2).getPostId(), "가장 오래된 포스트가 세 번째로 나와야 합니다.");
    }

    private PostEntity createPost(GroupEntity group, UserEntity user, String content, LocalDateTime createAt) {
        PostEntity post = new PostEntity();
        post.setGroup(group);
        post.setUser(user);
        post.setContent(content);
        post.setCreateAt(createAt);
        return post;
    }

    private GroupEntity createGroup(UserEntity user) {
        CreateGroupDTO createGroupDTO = CreateGroupDTO.builder()
                .groupName("groupName")
                .school("school")
                .grade(3)
                .groupClass(5)
                .userId(user.getUserId())
                .build();

        String userId = groupService.createGroup(createGroupDTO);

        Optional<GroupEntity> savedGroup = groupRepository.findByGroupCode(user.getGroup().getGroupCode());
        assertTrue(savedGroup.isPresent());
        return savedGroup.get();
    }

    private UserEntity createUser() {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .userId("userId")
                .userType("teacher")
                .password("password")
                .name("name")
                .build();

        userService.register(userRegisterDTO);

        Optional<UserEntity> savedUser = userRepository.findByUserId("userId");
        assertTrue(savedUser.isPresent(), "유저가 저장되어 있어야 합니다.");
        return savedUser.get();
    }
}