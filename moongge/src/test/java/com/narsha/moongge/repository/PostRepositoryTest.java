package com.narsha.moongge.repository;

import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

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
        PostEntity post1 = createPost(user, group, now.minusDays(2));
        PostEntity post2 = createPost(user, group, now.minusDays(1));
        PostEntity post3 = createPost(user, group, now);

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

    private PostEntity createPost(UserEntity user, GroupEntity group, LocalDateTime createAt) {
        PostEntity post = PostEntity.builder()
                .user(user)
                .group(group)
                .content("content")
                .createAt(createAt)
                .build();

        return postRepository.save(post);
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

    private UserEntity createUser() {
        UserEntity user = UserEntity.builder()
                .userId("userId")
                .userType("teacher")
                .password("password")
                .userName("name")
                .build();

        return userRepository.save(user);
    }
}