package com.narsha.moongge.service;

import com.narsha.moongge.base.dto.like.CreateLikeDTO;
import com.narsha.moongge.entity.GroupEntity;
import com.narsha.moongge.entity.LikeEntity;
import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;
import com.narsha.moongge.repository.GroupRepository;
import com.narsha.moongge.repository.LikeRepository;
import com.narsha.moongge.repository.PostRepository;
import com.narsha.moongge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeService likeService;
    @Autowired
    private LikeRepository likeRepository;

    @Test
    void 좋아요_생성하기() {

        // given
        UserEntity user = createUser();
        GroupEntity group = createGroup(user);
        PostEntity post = createPost(user, group);

        CreateLikeDTO createLikeDTO = buildCreateLikeDTO(user, group, post);

        // when
        Integer savedLikeId = likeService.createLike(group.getGroupCode(), post.getPostId(), createLikeDTO);

        // then
        Optional<LikeEntity> findLike = likeRepository.findById(savedLikeId);
        assertTrue(findLike.isPresent());

        LikeEntity like = findLike.get();

        assertEquals(savedLikeId, like.getLikeId());
        assertEquals(createLikeDTO.getGroupCode(), like.getGroup().getGroupCode());
        assertEquals(createLikeDTO.getPostId(), like.getPost().getPostId());
        assertEquals(createLikeDTO.getUserId(), like.getUser().getUserId());
    }

    private CreateLikeDTO buildCreateLikeDTO(UserEntity user, GroupEntity group, PostEntity post) {
        return CreateLikeDTO.builder()
                .groupCode(group.getGroupCode())
                .postId(post.getPostId())
                .userId(user.getUserId())
                .build();
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